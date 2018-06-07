package com.company.example.hazelcast.job;

import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.example.hazelcast.constants.Constants;
import com.hazelcast.core.HazelcastInstance;

public abstract class AbstractJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);

    protected static final String CATEGORY = "JOB";
    private String jobName;

    @Autowired
    private HazelcastInstance hazelcastInstance;


    public AbstractJob(String jobName) {
        this.jobName = jobName;
    }

    protected abstract void execute();

    public void run() {

        Lock lock = this.hazelcastInstance.getLock(Constants.JOB_LOCK);
        lock.lock();

        try {
            String ip = this.hazelcastInstance.getCluster()
                .getLocalMember()
                .getAddress()
                .getHost();
            System.out.println("Executing JOB in: " + ip);
            // run job
            this.execute();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
