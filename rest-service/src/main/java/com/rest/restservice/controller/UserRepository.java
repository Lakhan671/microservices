package com.rest.restservice.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.restservice.controller.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}
