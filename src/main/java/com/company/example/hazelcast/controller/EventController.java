package com.company.example.hazelcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.example.hazelcast.dto.EventDTO;
import com.company.example.hazelcast.service.internal.EventService;

@RestController()
@RequestMapping("/aftersale/rubick2/hazelcast/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping("")
	public Long publishEvent(@RequestBody EventDTO eventDTO) {
		try {
			return eventService.publishEvent(eventDTO);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/print")
	public void printEvents() {
		eventService.printEvents();
	}
	
}