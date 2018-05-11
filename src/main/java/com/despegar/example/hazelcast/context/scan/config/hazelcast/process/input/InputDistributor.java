package com.despegar.example.hazelcast.context.scan.config.hazelcast.process.input;



import org.springframework.stereotype.Service;

@Service
public class InputDistributor {
//        extends DistributorAbstractService<Map<Long, Event>>
//        implements IProcess<InputJobDTO> {
//
//    @Autowired
//    protected InputMapManager inputMapManager;
//    @Autowired
//    protected TaskMapManager taskMapManager;
//    @Autowired
//    InputProcessor processor;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(InputDistributor.class);
//
//    public void filterAndProcess(InputJobDTO job) {
//        this.process(job.getProductType(), InputProcessState.ACTIVE, null);
//    }
//
//    @Override
//    protected Predicate<String, ProductEx> filter(String productType, String state, String action) {
//
//        Predicate<String, ProductEx> nonNullPredicate = Objects::nonNull;
//        Predicate<String, ProductEx> stateNotNull = p -> p.getValue().getState() != null;
//        Predicate<String, ProductEx> statePredicate = p -> p.getValue().getState().equals(state);
//        Predicate<String, ProductEx> typeNotNull = p -> p.getValue().getType() != null;
//        Predicate<String, ProductEx> typePredicate = p -> p.getValue().getType().equals(productType);
//        Predicate<String, ProductEx> retriesPredicate = p -> p.getValue().getRetries() < MAX_RETRIES;
//
//        Predicate<String, ProductEx> datePredicate = p -> {
//            if (p.getValue().getLastRun() != null) {
//                Calendar calLastRun = Calendar.getInstance();
//                calLastRun.setTime(p.getValue().getLastRun());
//                calLastRun.add(Calendar.MINUTE, ZINCO * p.getValue().getRetries());
//                return Calendar.getInstance().getTimeInMillis() > calLastRun.getTimeInMillis();
//            } else {
//                return true;
//            }
//        };
//
//        Predicate<String, ProductEx> fullPredicate = Predicates.and(nonNullPredicate, stateNotNull, statePredicate, typeNotNull, typePredicate,
//                retriesPredicate, datePredicate);
//        PagingPredicate pagingPredicate = new PagingPredicate(fullPredicate, PAGE_SIZE);
//
//        return pagingPredicate;
//    }
//
//    @Override
//    protected void process(String productType, String state, String action) {
//        this.process(productType, InputProcessState.ACTIVE, action, this.processor, this.inputMapManager);
//    }
//
//    public void add(ReservationEx input, Map<String, Object> headers) {
//        IMap<String, ProductEx> map = this.inputMapManager.getMap();
//
//        List<ProductEx> productToAdd = new ArrayList<>();
//        input.getProducts().stream().forEach(p -> {
//
//            p.setState(InputProcessState.ACTIVE);
//            p.setReservationId(input.getReservationId());
//            p.setHeaders(headers);
//
//            if (p.getAction().equals(Action.UPDATE) || p.getAction().equals(Action.REJECT)) {
//                if (map.get(p.getKey()) == null) {
//                    throw new BusinessException(
//                            String.format("Product Key %s not match for update this Reservation %s", p.getKey(), p.getReservationId()));
//                } else {
//                    productToAdd.add(p);
//                }
//            } else if (map.get(p.getKey()) == null) {
//                productToAdd.add(p);
//            }
//        });
//
//        if (productToAdd.isEmpty()) {
//            throw new BusinessException(String.format("All Products already exist for Reservation %s", input.getReservationId()));
//        } else {
//            IMap<String, TaskData> taskMap = this.taskMapManager.getMap();
//            productToAdd.forEach(p -> validateCompatibleState(p, taskMap));
//        }
//        productToAdd.forEach(p -> map.set(p.getKey(), p));
//    }
//
//    protected void validateCompatibleState(ProductEx prod, IMap<String, TaskData> taskMap) {
//        validateState(prod, taskMap.get(prod.getKey()));
//    }
//
//    protected void validateState(ProductEx productEx, TaskData currentTaskData) {
//        if (currentTaskData == null)
//            return;
//        String currentState = currentTaskData.getContextData().getState();
//        if (!ProcessState.DONE.equals(currentState)) {
//            String errorMsg = "trying to update task in {} process. key: {}, data: {} with data: {}";
//            String currentAction = currentTaskData.getContextData().getAction();
//            LOGGER.error(errorMsg, currentAction, productEx.getKey(), currentTaskData.toString(), productEx.toString());
//            NewRelic.incrementCounter("Custom/inputs/bad_state");
//            throw new BusinessException(String.format("Could not process request, trying to update NOT DONE task in %s process, task: %s", currentAction, currentTaskData.getProduct().toString()));
//        }
//    }

}
