package com.selfio.selfio.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDataObject, Integer> {
}
