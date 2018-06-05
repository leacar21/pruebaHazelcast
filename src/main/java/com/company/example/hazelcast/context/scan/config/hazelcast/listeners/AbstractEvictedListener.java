package com.company.example.hazelcast.context.scan.config.hazelcast.listeners;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryEvictedListener;
import com.hazelcast.map.listener.EntryExpiredListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapClearedListener;
import com.hazelcast.map.listener.MapEvictedListener;

public abstract class AbstractEvictedListener implements EntryAddedListener, EntryRemovedListener, EntryUpdatedListener, EntryEvictedListener, EntryExpiredListener, MapEvictedListener, MapClearedListener, HazelcastInstanceAware {
    
	private HazelcastInstance instance;

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.instance = hazelcastInstance;
    }

    @Override
    public abstract void entryEvicted(EntryEvent event);

    abstract Boolean isEnabled();

    public IMap getMap(String key) {
        return instance.getMap(key);
    }
}
