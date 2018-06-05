package com.company.example.hazelcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.example.hazelcast.dto.UserDTO;
import com.company.example.hazelcast.service.internal.UserService;

@RestController()
@RequestMapping("/aftersale/rubick2/hazelcast/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable(value = "id") Long id) {
		return userService.getUserById(id);
	}
	
	@PostMapping("")
	public Long addUser(@RequestBody UserDTO userDTO) {
		return userService.addUser(userDTO);
	}
	
}
