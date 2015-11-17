package com.theironyard.services;

import com.theironyard.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by landonkail on 11/12/15.
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {
    Page<Contact> findAllByOrderByFirstNameAsc(Pageable pageable);

    Page<Contact> findAllByOrderByLastNameAsc(Pageable pageable);

}
