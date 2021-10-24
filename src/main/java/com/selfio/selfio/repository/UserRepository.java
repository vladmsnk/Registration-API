package com.selfio.selfio.repository;

import com.selfio.selfio.entities.UserDataObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDataObject, Integer> {
}
