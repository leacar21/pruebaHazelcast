package com.despegar.example.hazelcast.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.dto.EventDTO;
import com.despegar.example.hazelcast.service.internal.SubTeamService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

@Component
public class EventMessageListener implements MessageListener<EventDTO> {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private SubTeamService subTeamService;
	
	@Override
	public void onMessage(Message<EventDTO> message) {
		EventDTO eventDTO = message.getMessageObject();
		System.out.println("Event received with Id = " + eventDTO.getId());
		
		
		
		// Codigo mutoexcluido
//		Lock lock = hazelcastInstance.getLock("lockSubClassQueues");
//		lock.lock();
//		try {
//			Map<Long, SubTeamDTO> mapSubTeams = hazelcastInstance.getMap("listSubTeams");
//			Collection<SubTeamDTO> collectionSubTeamDTO = mapSubTeams.values();
//			//collectionSubTeamDTO.stream().forEach(subTeam -> this.subTeamService.addToSubteamQueue(subteam, 5));
//		} finally {
//		  lock.unlock();
//		}
		//---------------
		
		
	}

}
