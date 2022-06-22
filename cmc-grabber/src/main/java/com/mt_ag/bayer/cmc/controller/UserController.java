package com.mt_ag.bayer.cmc.controller;

import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.User;
import com.mt_ag.bayer.cmc.persistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private UserRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserRepository repository) {
		super();
		this.repository = repository;
	}

	// Demo till LDAP or Tokensystem
	@CrossOrigin
	@GetMapping("/users")
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		this.repository.findAll().forEach(users::add);
		return users;
	}

	@CrossOrigin
	@RequestMapping("/users/{id}")
	public User findById(@PathVariable Long id) {
		return this.repository.findById(id).get();
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public void saveUser(@RequestBody User user) {
		try {
			this.repository.save(user);
		} catch (Exception e) {
			LOGGER.info("Saving failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/users")
	public void updateUser(@RequestBody User user) {
		try {
			this.repository.save(user);
		} catch (Exception e) {
			LOGGER.info("Updating failed", e);
		}
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/users")
	public void deleteCountry(@RequestBody User user) {
		this.repository.delete(user);
	}

	@CrossOrigin
	@RequestMapping(value = "/login")
	public User loginWithUser(@RequestBody User user) {

		User userToFind = this.repository.findByUsername(user.getUsername());
	
		if (userToFind == null) {
			return new User(-2L, "User not found", "User not found");
		}
		if (userToFind.getPassword().equals(user.getPassword())) {
			return userToFind;
		} else {
			return new User(-1L, "Wrong Password", "Wrong Password");
		}
	}
}
