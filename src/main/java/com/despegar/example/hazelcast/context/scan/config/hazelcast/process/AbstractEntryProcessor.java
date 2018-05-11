package com.despegar.example.hazelcast.context.scan.config.hazelcast.process;

/**
 * Created by juanmartinez on 1/9/16.
 */
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.despegar.aftersale.doom.service.util.CopyValueToBackupProcessor;
import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;

public abstract class AbstractEntryProcessor<K, V>
                implements EntryProcessor<K, V>, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntryProcessor.class);
    protected static final int DONE_EVICTION_VALUE = 1;
    protected static final TimeUnit DONE_EVICTION_UNIT = TimeUnit.SECONDS;
    protected final int EVICTION_VALUE = 60;
    protected final TimeUnit EVICTION_UNIT = TimeUnit.SECONDS;
//    private final CopyValueToBackupProcessor<K, V> entryBackupProcessor;

    protected transient V value;

    public AbstractEntryProcessor() {
    }
    
//    public AbstractEntryProcessor() {
//        this(false);
//    }

//    public AbstractEntryProcessor(boolean applyOnBackup) {
//        if (applyOnBackup) {
//            entryBackupProcessor = new CopyValueToBackupProcessor<>(this.value);
//        } else {
//            entryBackupProcessor = null;
//        }
//    }

    @Override
    public abstract Object process(Map.Entry<K, V> entry);

//    @Override
//    public EntryBackupProcessor<K, V> getBackupProcessor() {
//        return this.entryBackupProcessor;
//    }
    
}
