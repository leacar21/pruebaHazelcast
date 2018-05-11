package com.despegar.example.hazelcast.context.scan.config.hazelcast.process;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.despegar.example.hazelcast.context.scan.config.hazelcast.map.IMapManager;
import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.query.Predicate;

/**
 * Created by juanmartinez on 13/9/16.
 */
public abstract class DistributorAbstractService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributorAbstractService.class);

    protected static final int MAX_RETRIES = 3;
    protected final int PAGE_SIZE = 5;
    protected final int ZINCO = 5;

    // Process methods
    protected abstract void process(String productType, String state, String action);

    protected void process(String productType, String state, String action, EntryProcessor processor, IMapManager mapManager) {
        if (this.processActive(productType)) {
            // Filter
            IMap<String, T> map = mapManager.getMap();
            Predicate predicate = this.filter(productType, state, action);

            // local keys, la distribucion de carga la realizó HZ al distribuir las claves en las particiones.
            Set<String> keySet = map.localKeySet(predicate);
            if (!CollectionUtils.isEmpty(keySet)) {
                LOGGER.info("PROCESING: Type: {}, State: {}, Keys: {}", productType, state, Arrays.toString(keySet.toArray()));
                map.executeOnKeys(keySet, processor);
            }

        } else {
            LOGGER.info("PROCESS OFF for {}", productType);
        }

    }


    protected void callback(List<ICompletableFuture> futures) {
        futures.stream().forEach(f -> {
            try {
                f.andThen(new ExecutionCallback<String>() {
                    public void onResponse(String response) {
                        LOGGER.info("OK: " + response);
                    }

                    public void onFailure(Throwable t) {
                        LOGGER.info("FAILURE: " + t);
                    }
                });
            } catch (Exception e) {
                LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            }
        });
    }

    // Commons methods
    protected abstract Predicate<String, T> filter(String productType, String state, String action);


    //TODO: Ver de aplicar el active por Type y Action
    protected boolean processActive(String productType) {
//        String value = this.processMapManager.getMap().get(productType);
//        if (value != null) {
//            return value.equals("ON");
//        } else {
//            this.processMapManager.getMap().set(productType, "OFF");
//            return false;
//        }
    		return false;
    }


}
