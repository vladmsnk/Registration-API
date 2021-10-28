package com.selfio.selfio.repository;

import com.selfio.selfio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    User finByEmail(String email);
}
