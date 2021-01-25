package com.skcc.narubatch.scheduler;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skcc.narubatch.eigwMonitoringJob.EigwMonitoringJob;
import com.skcc.narubatch.sampleJob2.SampleJob2;

@Component
public class SampleScheduler {
	private SchedulerFactory schedulerFactory;
	private Scheduler scheduler;
	
//	@Autowired
//	private SampleJob2 sambpleJob2;
	
	@PostConstruct
	public void start() throws SchedulerException {
		schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		
		JobDetail job = JobBuilder.newJob(EigwMonitoringJob.class).withIdentity("eigwMon").build();
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build();
		
		JobDetail job2 = JobBuilder.newJob(SampleJob2.class).withIdentity("sampleJob2").build();
		Trigger trigger2 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
		
		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(job2, trigger2);
	}
}
