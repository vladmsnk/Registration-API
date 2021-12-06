package com.selfio.selfio.repo;

import com.selfio.selfio.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDetailsRepo extends CrudRepository<User, String> {

    Optional<User> findByGoogleID(String googleLogin);

    Optional<User> findByEmail(String email);

}
