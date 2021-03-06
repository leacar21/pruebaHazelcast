package com.company.example.hazelcast.controller;


import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.example.hazelcast.constants.Constants;
import com.company.example.hazelcast.repository.model.Event;
import com.company.example.hazelcast.service.EventService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@RestController()
@RequestMapping("/example/hazelcast")
public class EventController {
	
	@Autowired
    private HazelcastInstance hazelcastInstance;
	
	@Autowired
	private EventService eventService;
	
//	@PostMapping("")
//	public Long addEvent(@RequestBody Event event) {
//		IMap<Long, Event> map = this.hazelcastInstance.getMap(Constants.EVENTS_MAP);
//		Long key = this.generateKey();
//		map.put(key, event);
//		return key;
//	}
	
	@PostMapping("")
	public Long addEventToProcess(@RequestBody Event event) {
		Long key = this.generateKey();
		event.setKey(key);
		eventService.addEventToProcess(event);
		return key;
	}
	
	@GetMapping("/localKeys")
    public Set<Long> getLocalKeys() {
        IMap<Long, Event> map = this.hazelcastInstance.getMap(Constants.EVENTS_MAP);
        Set<Long> localKeySet = map.localKeySet();
        System.out.println("===========>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Local Keys Set: " + localKeySet);
        return localKeySet;
    }
	
	//-------
	
	private Long generateKey() {
		Date date = new Date();
		return date.getTime();
	}
	
}
