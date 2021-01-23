package com.skcc.narubatch.sampleJob2;

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
import org.springframework.stereotype.Component;

@Component
public class SampleScheduler2 {
	private SchedulerFactory schedulerFactory;
	private Scheduler scheduler;
	
	@PostConstruct
	public void start() throws SchedulerException {
		schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		
		JobDetail job = JobBuilder.newJob(SampleJob2.class).withIdentity("sampleJob2").build();
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("*/7 * * * * ?")).build();
		
		scheduler.scheduleJob(job, trigger);
	}
}
