package com.despegar.example.hazelcast.context.scan.config.hazelcast.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.despegar.example.hazelcast.repository.dao.subteam.SubTeamDAO;
import com.despegar.example.hazelcast.repository.model.SubTeam;
import com.hazelcast.core.MapStore;

@Component
public class SubTeamMapStore implements MapStore<Long, SubTeam>{

    
    @Autowired
    private SubTeamDAO subTeamDAO;

    public synchronized void delete(Long key) {
    		System.out.println(">>>>> SubTeam MAP delete");
    		subTeamDAO.delete(key); 			
    }

    public synchronized void store(Long key, SubTeam value) {
    		System.out.println(">>>>> SubTeam MAP store");
    		subTeamDAO.save(value);
    }

    public synchronized void storeAll(Map<Long, SubTeam> map) {
        for (Map.Entry<Long, SubTeam> entry : map.entrySet())
            store(entry.getKey(), entry.getValue());
    }

    public synchronized void deleteAll(Collection<Long> keys) {
        for (Long key : keys) delete(key);
    }

    public synchronized SubTeam load(Long key) {
    		System.out.println(">>>>> SubTeam MAP load");
    		SubTeam subTeam = subTeamDAO.read(key);
    		return subTeam;
    }

    public synchronized Map<Long, SubTeam> loadAll(Collection<Long> keys) {
    		System.out.println(">>>>> SubTeam MAP loadAll");
        Map<Long, SubTeam> result = new HashMap<Long, SubTeam>();
        for (Long key : keys) { 
        		result.put(key, load(key));
        }
        return result;
    }

    public Iterable<Long> loadAllKeys() {
    		System.out.println(">>>>> SubTeam MAP loadAllKeys");
    		List<Long> list = subTeamDAO.loadAllKeys();
        Set<Long> result = new HashSet<Long>(list);
        return result;
    }
}
