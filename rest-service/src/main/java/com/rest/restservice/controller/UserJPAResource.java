/**
 * 
 */
package com.rest.restservice.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.restservice.controller.model.User;
import com.rest.restservice.exception.NoUserFoundException;
import com.rest.restservice.service.UserDaoService;

/**
 * @author Lakhan
 *
 */
@RestController
@RequestMapping(path = "/jpa")
public class UserJPAResource {
	final static Logger logger = Logger.getLogger(UserResource.class);
	@Autowired
	private UserDaoService userDaoService;
	@Autowired
    private UserRepository userRepository;
	@GetMapping(path = "/users")
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	@GetMapping(path = "/users/{id}")
	public Resource<User> finduser(@PathVariable int id) {
		
		try {
			
			Optional<User> user= userRepository.findById(id);
			Resource<User>resource=new Resource<User>(user.get());
			resource.add(linkTo(methodOn(this.getClass()).getAllUser()).withRel("all-user"));
			return resource;
		} catch (Exception e) {
			throw new NoUserFoundException("id="+id);
		}
	}
	
	@DeleteMapping(path = "/users/{id}")
	public List<User> delete (@PathVariable int id) {
		userRepository.deleteById(id);
		return userRepository.findAll();
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
