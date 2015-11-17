package com.theironyard.controllers;

import com.theironyard.entities.Contact;
import com.theironyard.entities.User;
import com.theironyard.services.ContactRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.util.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by landonkail on 11/12/15.
 */
@Controller
public class CRMController {
    @Autowired
    UserRepository users;

    @Autowired
    ContactRepository contacts;

    @PostConstruct
    public void init() {
        if (contacts.count() == 0) {
            addJohnContact();
        }

        if (users.count() == 0) {
            addMichaelUser();
        }
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page, String lastName, String firstName) {
        String username = (String) session.getAttribute("username");
        PageRequest pr = new PageRequest(page, 5);

        Page p;

        if (username == null) {
            return "login";
        }
        model.addAttribute("user", users.findOneByUsername(username));

        model.addAttribute("showFirstName", contacts.findAllByOrderByFirstNameAsc(p, firstName));
        model.addAttribute("showLastName", contacts.findAllByOrderByLastNameAsc(lastName));

        p = contacts.findAllByOrderByFirstNameAsc(pr);
        p2 = contacts.findAllByOrderByLastNameAsc(pr);
        model.addAttribute("contact", p);
\

        // Next Page buttons
        model.addAttribute("showNext", p.hasNext());
        model.addAttribute("nextPage", page+1);


        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findOneByUsername(username);

        if (user == null) {
            return "register";
        }

        if (PasswordHash.validatePassword(password, user.password)) {
            session.setAttribute("username", username);
            return "redirect:/";
        } else {
            return "login";
        }

    }

    @RequestMapping("/register")
    public String register(HttpSession session, String username, String password, String email, String firstname, String lastname, String confirmpassword) throws Exception {
        session.setAttribute("username", username);

        User user = users.findOneByUsername(username);

        if (user != null) {
            throw new Exception("Username Already Exists");
        }

        if (!password.equals(confirmpassword)) {
            throw new Exception("Passwords Do Not match");
        }


        user = new User();

        user.username = username;
        user.password = PasswordHash.createHash(password);
        user.email = email;
        user.firstName = firstname;
        user.lastName = lastname;

        users.save(user);

        return "redirect:/";

    }

    @RequestMapping("/showAddContact")
    public String showAdd(HttpSession session) throws Exception {
        if (session.getAttribute("username") == null) {
            throw new Exception("Not logged in");
        }

        return "addContact";
    }

    @RequestMapping("/addContact")
    public String addContact(HttpSession session, String firstname, String lastname, String email, String phone, String address, String city, String state, String zip, String company, String jobtitle, String notes) throws Exception {
        if (session.getAttribute("username") == null) {
            throw new Exception("Not logged in");
        }

        String username = (String) session.getAttribute("username");
        User user = users.findOneByUsername(username);

        Contact c = new Contact();

        c.firstName = firstname;
        c.lastName = lastname;
        c.email = email;
        c.phone = phone;
        c.address = address;
        c.city = city;
        c.state = state;
        c.zip = zip;
        c.company = company;
        c.jobTitle = jobtitle;
        c.notes = notes;
        c.user = user;

        contacts.save(c);

        return "redirect:/";

    }


    @RequestMapping("/details")
    public String details(Model model, HttpSession session, Integer id) {
        if (session.getAttribute("username") == null) {
            return "login";
        }

        model.addAttribute("contact", contacts.findOne(id));

        return "details";
    }

    @RequestMapping("/delete-message")
    public String deleteContact(Integer id, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "login";
        }

        contacts.delete(id);


        return "redirect:/";

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }


    public void addJohnContact() {
        Contact c = new Contact();
        c.firstName = "John";
        c.lastName = "Smith";
        c.email = "john.smith123@gmail.com";
        c.phone = "843-123-1234";
        c.address = "123 Main";
        c.city = "Charleston";
        c.state = "SC";
        c.zip = "29403";
        c.company = "The Iron Yard";
        c.jobTitle = "The Boss";
        c.firstContact = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        c.lastContact = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        c.notes = "This is a note";

        contacts.save(c);
    }

    public void addMichaelUser () {
        User u = new User();
        u.firstName = "Michael";
        u.lastName = "Bolton";
        u.username = "mbolton123";
        u.email = "michael.bolton123@gmail.com";
        u.password = "treeman100";

        users.save(u);
    }
}
