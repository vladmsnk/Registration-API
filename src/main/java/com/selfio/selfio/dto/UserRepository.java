package com.selfio.selfio.dto;

import com.selfio.selfio.entities.UserDataObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDataObject, Integer> {
}
