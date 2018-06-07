package com.company.example.hazelcast.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.company.example.hazelcast.service.EventService;

@Component
public class ProcessQueueJob
    extends AbstractJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessQueueJob.class);

    private static final String PROCESS_EVENT_TO_PROCESS = "PROCESS_EVENT_TO_PROCESS";

    @Autowired
    private EventService eventService;


    public ProcessQueueJob() {
        super(PROCESS_EVENT_TO_PROCESS);
    }

    @Override
    @Scheduled(initialDelayString = "5000", fixedRateString = "3000")
    public void run() {
        super.run();
    }

    // -----

    @Override
    protected void execute() {
        this.eventService.processNextEventToProcess();
    }
    
    // -----

}
