package com.rest.restservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static  org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.restservice.controller.model.User;
import com.rest.restservice.exception.NoUserFoundException;
import com.rest.restservice.service.UserDaoService;


@RestController
public class UserResource {
	final static Logger logger = Logger.getLogger(UserResource.class);
	@Autowired
	private UserDaoService userDaoService;

	@GetMapping(path = "/users")
	public List<User> getAllUser() {
		return userDaoService.findAll();
	}
	@GetMapping(path = "/users/{id}")
	public Resource<User> finduser(@PathVariable int id) {
		
		try {
			
			User user= userDaoService.getuser(id);
			Resource<User>resource=new Resource<User>(user);
			resource.add(linkTo(methodOn(this.getClass()).getAllUser()).withRel("all-user"));
			return resource;
		} catch (Exception e) {
			throw new NoUserFoundException("id="+id);
		}
	}
	
	@DeleteMapping(path = "/users/{id}")
	public List<User> delete (@PathVariable int id) {
		return userDaoService.deleteUser(id);
	}
	
	@PostMapping(path = "/users/{id}")
	public ResponseEntity<Object> save (@Valid @RequestBody User user,@PathVariable int id) {
		logger.debug("========================================================");
		logger.debug(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(1).toUri());
		logger.debug("=========================================================");
		 userDaoService.saveUser(user);
	   return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(2).toUri()).build();
		
	}
	
}
