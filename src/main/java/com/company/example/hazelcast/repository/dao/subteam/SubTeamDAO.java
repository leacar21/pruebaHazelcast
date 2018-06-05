package com.company.example.hazelcast.repository.dao.subteam;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.company.example.hazelcast.repository.dao.AbstractHibernateEntityDAO;
import com.company.example.hazelcast.repository.model.SubTeam;

@Component
public class SubTeamDAO
    extends AbstractHibernateEntityDAO<SubTeam, Long> {

    public List<Long> loadAllKeys() {
		List<Long> keys = new LinkedList<Long>();
		List<SubTeam> listSubTeam = this.readAll();
		for (SubTeam subTeam : listSubTeam) {
			keys.add(subTeam.getId());
		}
        return keys; 
    }
	

}
