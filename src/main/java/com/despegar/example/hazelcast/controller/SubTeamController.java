package com.despegar.example.hazelcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.despegar.example.hazelcast.dto.SubTeamDTO;
import com.despegar.example.hazelcast.service.internal.SubTeamService;

@RestController()
@RequestMapping("/aftersale/rubick2/hazelcast/subteam")
public class SubTeamController {
	
	@Autowired
	private SubTeamService subTeamService;
	
	@PostMapping("")
	public Long addSubTeam(@RequestBody SubTeamDTO subTeamDTO) {
		return subTeamService.addSubTeam(subTeamDTO);
	}
	
}