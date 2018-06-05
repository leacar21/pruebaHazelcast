package com.company.example.hazelcast.context.scan.config.hazelcast.map;

import com.hazelcast.core.IMap;

public interface IMapManager<K, V> {

    IMap<K, V> getMap();
}
