package com.theironyard;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by landonkail on 11/12/15.
 */
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    List<Contact> findAllByOrderByFirstNameAsc();

}
