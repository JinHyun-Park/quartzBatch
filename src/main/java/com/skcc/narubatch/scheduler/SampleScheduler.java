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
import org.springframework.stereotype.Component;

import com.skcc.narubatch.job.SampleJob;

@Component
public class SampleScheduler {
	private SchedulerFactory schedulerFactory;
	private Scheduler scheduler;
	
	@PostConstruct
	public void start() throws SchedulerException {
		schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		
		JobDetail job = JobBuilder.newJob(SampleJob.class).withIdentity("sampleJob").build();
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("30 * * * * ?")).build();
		
		scheduler.scheduleJob(job, trigger);
	}
}
