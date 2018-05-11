package com.despegar.example.hazelcast.context.scan.config.hazelcast.process.common;

/**
 * Created by didier on 02/12/16.
 */
public interface IProcess<T> {
    void filterAndProcess(T job);
}
