package com.despegar.example.hazelcast.context.scan.config.hazelcast.map;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public abstract class AbstractManagerMap<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractManagerMap.class);

    @Autowired
    protected HazelcastInstance hazelcast;

    public abstract IMap<K, V> getMap();

    public void add(K key, V value) {
        this.getMap().set(key, value);
    }


    public void update(K key, V value, int eviction_value, TimeUnit eviction_unit) {
        this.getMap().set(key, value, eviction_value, eviction_unit);
    }

    public void remove(String item) {
        this.getMap().remove(item);
    }

    public Cluster getCluster() {
        return this.hazelcast.getCluster();
    }

    public void refreshMap() {
        this.getMap().evictAll();
        this.getMap().loadAll(true);
    }
}
