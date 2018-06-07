package com.company.example.hazelcast.context.scan.config.hazelcast.listeners;


import org.springframework.stereotype.Component;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.MapEvent;

@Component
public class EventListener extends AbstractEvictedListener {
    public EventListener() {
    }

	@Override
	Boolean isEnabled() {
		return true;
	}
    
    @Override
    public void entryEvicted(EntryEvent event) {
    		//System.out.println( "-----> Entry Evicted:" + event );
    }

    @Override
    public void entryExpired(EntryEvent event) {
    		//System.out.println( "-----> Entry Expired:" + event );
    }

	@Override
	public void entryAdded(EntryEvent event) {
		//System.out.println( "-----> Entry Added:" + event );
	}
 
	@Override
	public void entryRemoved(EntryEvent event) {
		//System.out.println( "-----> Entry Removed:" + event );
	}

	@Override
	public void entryUpdated(EntryEvent event) {
		//System.out.println( "-----> Entry Updated:" + event);
	}

	@Override
	public void mapEvicted(MapEvent event) {
		//System.out.println( "-----> Map Evicted:" + event );
	}

	@Override
	public void mapCleared(MapEvent event) {
		//System.out.println( "-----> Map Cleared:" + event );
	}
}
