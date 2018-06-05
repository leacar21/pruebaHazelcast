package com.company.example.hazelcast.job;

import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.HazelcastInstance;

public abstract class AbstractJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);
	
	protected static final String CATEGORY = "JOB";
	private static final String JOB_LOCK = "jobLock";
	
	private static final String MAP_EXCECUTE = "map_excecute";
	private static final String EXCECUTE = "excecute";

	private String jobName;

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
//	@Autowired
//	private JobDataService jobDataService;

	public AbstractJob(String jobName) {
		this.jobName = jobName;
	}

	protected abstract void execute();

	public void run() {

		Lock lock = hazelcastInstance.getLock(JOB_LOCK);
		lock.lock();

		try {
			String ip = hazelcastInstance.getCluster().getLocalMember().getAddress().getHost();
			System.out.println("Ejecutando JOB en: " + ip);
			// run job
			this.execute();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			e.printStackTrace();
		} finally {
			lock.unlock();
		}		

	}
	
//	public void run() {
//
//		Lock lock = hazelcastInstance.getLock(JOB_LOCK);
//		lock.lock();
//		Map<String, Boolean> mapExcecute = hazelcastInstance.getMap(MAP_EXCECUTE);
//		Boolean excecute = mapExcecute.get(EXCECUTE);
//		Boolean localExcecute = Boolean.FALSE;
//		
//		if ((excecute==null) || !excecute) {
//			mapExcecute.put(EXCECUTE, Boolean.TRUE);
//			localExcecute = Boolean.TRUE;
//		} 
//		lock.unlock();
//		
//		if (localExcecute) {
//			try {
//				String ip = hazelcastInstance.getCluster().getLocalMember().getAddress().getHost();
//				System.out.println("Ejecutando JOB en: " + ip);
//				// run job
//				this.execute();
//			} catch (Exception e) {
//				System.out.println("ERROR: " + e);
//				e.printStackTrace();
//			} finally {
//				lock.lock();
//				mapExcecute.put(EXCECUTE, Boolean.FALSE);
//				lock.unlock();
//			}
//		}
//
//	}

//	/**
//	 * Check if job is running on this host and initialize if required
//	 */
//	protected void check() {
//		JobData jobData = this.jobDataService.getJobData(this.jobName);
//		if (jobData == null) {
//			return;
//		}
//		String hostName = this.getHostName();
//		boolean isRunning = JobStatusEnum.RUNNING.equals(jobData.getStatus());
//		boolean isHost = hostName.equals(jobData.getHostName());
//		if (isRunning && isHost) {
//			this.unlock(hostName);
//		}
//	}
//
//	/**
//	 * Return host name for Job
//	 * 
//	 * @return
//	 */
//	protected String getHostName() {
//		try {
//			return InetAddress.getLocalHost().getHostName();
//		} catch (Exception e) {
//			LOGGER.info("Job {} can not get host name at {}", this.jobName, (DateUtils.getUTCDate()).toString());
//		}
//		return "";
//	}
//
//	/**
//	 * This method synchronize Jobs, only one can set STATUS='RUNNING'
//	 * 
//	 * @param hostName
//	 * @return true if can run or false in other case
//	 */
//	protected boolean adquireLock(String hostName) {
//		JobData jobData = this.jobDataService.getJobData(this.jobName);
//		if (jobData == null) {
//			LOGGER.info("JobData for job {} is null at date {}", this.jobName, (DateUtils.getUTCDate()).toString());
//			return false;
//		}
//		if (jobData.isEnable()) {
//
//			boolean inTime = (jobData.getNextRun() == null);
//			if (jobData.getNextRun() != null) {
//				inTime = jobData.getNextRun().before(DateUtils.getUTCDate());
//			}
//
//			if (inTime) {
//				boolean adquireLock = this.jobDataService.adquireLock(hostName, this.jobName);
//				if (adquireLock) {
//					LOGGER.info("Host {} adquire lock for job {}", hostName, this.jobName + " at " + (DateUtils.getUTCDate()).toString());
//					return true;
//				} else {
//					LOGGER.info("Host {} not run job {}", hostName, this.jobName + " because other job is running at " + (DateUtils.getUTCDate()).toString());
//					return false;
//				}
//			} else {
//				LOGGER.info("Host {} because not in time at {}", hostName, this.jobName + " not run job " + (DateUtils.getUTCDate()).toString());
//				return false;
//			}
//		} else {
//			LOGGER.info("Job {} is not active at date {}", this.jobName, (DateUtils.getUTCDate()).toString());
//			return false;
//		}
//	}
//	
//	protected void unlock(String hostName) {
//		JobData jobData = this.jobDataService.getJobData(this.jobName);
//		// Set STATUS='WAITING'
//		boolean unlock = this.jobDataService.unlock(this.jobName, jobData.getDelaySeconds());
//		if (unlock) {
//			LOGGER.info("Job {} finish on host {}", this.jobName, hostName + " at " + (DateUtils.getUTCDate()).toString());
//		} else {
//			LOGGER.error("Job {} finish on {} but not unlock JobData!", this.jobName, hostName);
//		}
//	}
//
//	private boolean isLockToLongTime(String hostName) {
//		JobData jobData = this.jobDataService.getJobData(this.jobName);
//		if (jobData == null) {
//			LOGGER.info("JobData for job {} is null at date {}", this.jobName, (DateUtils.getUTCDate()).toString());
//			return false;
//		}
//		if (jobData.isEnable()) {
//			boolean hasLock = this.jobDataService.isLockToLongTime(this.jobName, jobData.getLastRunEnd());
//			if (hasLock) {
//				LOGGER.info("Host {} determine that lock job to long time {}", hostName, this.jobName + " at " + (DateUtils.getUTCDate()).toString());
//				return true;
//			} else {
//				LOGGER.info("Host {} determine that no lock job to long time {}", hostName, this.jobName + " at " + (DateUtils.getUTCDate()).toString());
//				return false;
//			}
//		} else {
//			LOGGER.info("Job {} is not active at date {}", this.jobName, (DateUtils.getUTCDate()).toString());
//			return false;
//		}
//	}
//
//	protected void noticeError(Exception exception, Map<String, String> params) {
//
//		String hostName = this.getHostName();
//		LOGGER.error("Job {} has an unexpected error running on {} Exeption: {}", this.jobName, hostName + " Exeption: " + ExceptionUtils.getStackTrace(exception));
//		
//		// TODO: Enviar o notificar del error a alguien.
//	}



}


