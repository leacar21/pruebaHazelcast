package com.despegar.example.hazelcast.context.scan.config.hazelcast.process.input;

import org.springframework.stereotype.Service;

/**
 * Created by Didier on 23/11/16.
 */
@Service
public class InputProcessor {
//                extends AbstractEntryProcessor<SubTeamTaskTuple, Map<Long, Event>> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(InputProcessor.class);
//
//    @Autowired
//    InputMapManager inputMapManager;
//
//    @Autowired
//    TaskMapManager taskMapManager;
//
//    @Autowired
//    ITaskDataService taskDataService;
//
//
//
//    @PostConstruct
//    public void init() {
//        // this.taskMapManager.getMap().addEntryListener(new TaskListener(), true);
//    }
//
//    public Object process(Map.Entry<SubTeamTaskTuple, Map<Long, Event>> entry) {
//    	
//    		Map<Long, Event> mapEvent = (Map<Long, Event>)(entry.getValue());
//		System.out.println( "-----> Entry Added:" + mapEvent.keySet() + " | " + entry);
//		SubTeamTaskTuple subTeamTaskTuple = (SubTeamTaskTuple)(entry.getKey());
//		
//		Long subTeamId = subTeamTaskTuple.getSubTeamId();
//		String subTeamMapName = Constants.EVENTS_MAP + Constants.SUB_TEAM_PREFIX + subTeamId;
//
//		Set<Long> keys = mapEvent.keySet();
//		Long minEventId = Collections.min(keys);
//		Event e = mapEvent.get(minEventId);
//		// TODO: Procesar evento para SubTeam
//		mapEvent.remove(minEventId);
//		Event e2 = mapEvent.get(minEventId);
//		this.inputMapManager.update(entry.getKey(), mapEvent);
//
//        return null;
//    }
//
//    private boolean alreadyExists(String taskKey) {
//        return this.taskMapManager.getMap().get(taskKey) != null;
//    }
//
//    @Trace(metricName = "services/tasks",
//                    dispatcher = true)
//    protected void complete(Map.Entry<String, ProductEx> entry) {
//
//        ProductEx product = entry.getValue();
//        product.setRetries(0);
//        product.setError("");
//        product.setState(InputProcessState.PREPARED);
//        product.setRunning(false);
//
//        this.inputMapManager.update(product, DONE_EVICTION_VALUE, DONE_EVICTION_UNIT);
//        LOGGER.info("PROCESS RESPONSE: key: {}, state: {}, ", entry.getKey(), product.getState());
//    }
//
//
//    private void error(ProductEx product, Throwable te) {
//        if (te != null) {
//            product.setError(te.getMessage());
//            product.setLastRun(DateTime.now().toDate());
//            this.inputMapManager.update(product);
//        }
//    }
//
//    protected void updateRun(ProductEx product) {
//        product.setRunning(true);
//        product.setRetries(product.getRetries() + 1);
//        product.setLastRun(DateTime.now().toDate());
//        this.inputMapManager.update(product);
//    }

}
