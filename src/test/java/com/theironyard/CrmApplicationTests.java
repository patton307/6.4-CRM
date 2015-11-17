package com.theironyard;

import com.theironyard.services.ContactRepository;
import com.theironyard.services.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CrmApplication.class)
@WebAppConfiguration
public class CrmApplicationTests {

    @Autowired
    ContactRepository contacts;

    @Autowired
    UserRepository users;

    @Autowired
    WebApplicationContext wap;

    MockMvc mockmvc;

    boolean started = false;

    @Before
    public void before() throws Exception {
        if (!started) {
            contacts.deleteAll();
            users.deleteAll();
            started = true;
        }

        mockmvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

    @Test
    public void testRegister() throws Exception {
        mockmvc.perform(
                MockMvcRequestBuilders.post("/register")
                .param("username", "testUser")
                .param("password", "testPass")
                .param("confirmpassword", "testPass")
                .param("email", "test@email.com")
                .param("firstname", "testFirst")
                .param("lastname", "testLast")
        );

        assertTrue(users.count() == 1);
    }

    @Test
    public void testaddContact() throws Exception {
        mockmvc.perform(
                MockMvcRequestBuilders.post("/addContact")
                .sessionAttr("username", "testUser")
                .param("firstname", "Landon")
                .param("lastname", "Kail")
                .param("email", "landon@email.com")
                .param("phone", "843-555-3434")
                .param("address", "123 Main")
                .param("city", "Charleston")
                .param("state", "SC")
                .param("zip", "29403")
                .param("company", "The Iron Yard")
                .param("jobTitle", "Manager")
                .param("notes", "This is a note")
        );

        assertTrue(contacts.count() == 1);
    }

    }



