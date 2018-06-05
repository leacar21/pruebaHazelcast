package com.company.example.hazelcast.service.internal;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.example.hazelcast.dto.EventDTO;
import com.company.example.hazelcast.dto.SubTeamDTO;
import com.company.example.hazelcast.dto.UserDTO;
import com.company.example.hazelcast.listener.EventMessageListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

@Service
public class SubTeamService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubTeamService.class);
	
	private static final String EVENTS_TOPIC = "events_topic";
	
	@Autowired
	private HazelcastInstance hazelcastInstance;
	

	@PostConstruct
	public void init() {
	    
	}
	
	public Long addSubTeam(SubTeamDTO subTeamDTO) {
		
		// Codigo mutoexcluido
		Lock lock = hazelcastInstance.getLock("lockSubClass");
		lock.lock();
		try {
			Map<Long, SubTeamDTO> mapSubTeams = hazelcastInstance.getMap("listSubTeams");
			subTeamDTO.setId(this.generateId());
			mapSubTeams.putIfAbsent(this.generateId(), subTeamDTO);	  
		} finally {
		  lock.unlock();
		}
		//---------------
		
		return subTeamDTO.getId();
	}
	
	//-------
	
	

	//-------
	
	private Long generateId() {
		Date now = new Date();
		Random random = new Random();
		Long id = now.getTime()*100000 + (random.nextLong()%100000);
		return id;
	}
	
}
