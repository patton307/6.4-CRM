package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by landonkail on 11/12/15.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findOneByUsername(String username);

}
