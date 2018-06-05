package com.company.example.hazelcast.service.internal;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.example.hazelcast.dto.UserDTO;
import com.hazelcast.core.HazelcastInstance;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	public UserDTO getUserById(Long id) {
		Map<Long, UserDTO> mapUsers = hazelcastInstance.getMap("users");
		UserDTO userDTO = mapUsers.get(id);
		return userDTO;
	}

	public Long addUser(UserDTO userDTO) {
		Map<Long, UserDTO> mapUsers = hazelcastInstance.getMap("users");
		
		Long id = this.generateId();
		userDTO.setId(id);
		
		mapUsers.putIfAbsent(id, userDTO);
		
		LOGGER.info("Tamaño del mapa Users: " + mapUsers.size());
		
		return id;
	}

	private Long generateId() {
		Date now = new Date();
		Random random = new Random();
		Long id = now.getTime()*100000 + (random.nextLong()%100000);
		return id;
	}
	
}
