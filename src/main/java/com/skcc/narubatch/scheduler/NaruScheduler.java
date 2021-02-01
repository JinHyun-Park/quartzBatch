package com.skcc.narubatch.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.skcc.narubatch.eigwMonitoringJob.EigwMonitoringJob;
import com.skcc.narubatch.sampleJob2.SampleJob2;

@Component
public class NaruScheduler {
	private SchedulerFactory schedulerFactory;
	private Scheduler scheduler;
	
//	@Autowired
//	private SampleJob2 sambpleJob2;
	@Autowired
	EigwMonitoringJob eigwMonitoringJob;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@PostConstruct
	public void start() throws SchedulerException {
		schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
		
		AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();
	    autowiringSpringBeanJobFactory.setApplicationContext(applicationContext);
	    scheduler.setJobFactory(autowiringSpringBeanJobFactory);
		
		scheduler.start();
		
		JobDetail job = JobBuilder.newJob().ofType(EigwMonitoringJob.class).storeDurably()
				.withIdentity("eigwMon").withDescription("eigwMon run").build();
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build();
		
		JobDetail job2 = JobBuilder.newJob().ofType(SampleJob2.class).storeDurably()
				.withIdentity("sampleJob2").withDescription("sampleJob2 run").build();
		Trigger trigger2 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
		
		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(job2, trigger2);
	}
	
	@PreDestroy
	public void cleanup(){
		schedulerFactory = null;
	    try {
	        scheduler.shutdown();
	    } catch (SchedulerException e) {
	        e.printStackTrace();
	    }
	}
	
//	@Bean
//	public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, DataSource quartzDataSource) {
//	    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//	    schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
//
//	    schedulerFactory.setJobFactory(springBeanJobFactory());
//	    schedulerFactory.setJobDetails(job);
//	    schedulerFactory.setTriggers(trigger);
//	    schedulerFactory.setDataSource(quartzDataSource);
//	    return schedulerFactory;
//	}
//	
//	@Bean
//	public SpringBeanJobFactory springBeanJobFactory() {
//	    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
//	    jobFactory.setApplicationContext(applicationContext);
//	    return jobFactory;
//	}
//	@Bean
//	public SchedulerFactoryBean quartzScheduler() {
//	    SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
//
//	    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//	    jobFactory.setApplicationContext(applicationContext);
//	    quartzScheduler.setJobFactory(jobFactory);
//
//	    return quartzScheduler;
//	}
//	 public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory{
//
//		    private AutowireCapableBeanFactory beanFactory;
//
//		    public AutowiringSpringBeanJobFactory(final ApplicationContext applicationContext){
//		        beanFactory = applicationContext.getAutowireCapableBeanFactory();
//		    }
//
//		    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
//		        final Object job = super.createJobInstance(bundle);
//		        beanFactory.autowireBean(job);
//		        beanFactory.initializeBean(job, job.getClass().getName());
//		        return job;
//		    }
//		}
//
//
//		@Configuration
//		public class SchedulerConfig {   
//		    @Autowired private ApplicationContext applicationContext;
//
//		    @Bean
//		    public AutowiringSpringBeanJobFactory getAutowiringSpringBeanJobFactory(){
//		        return new AutowiringSpringBeanJobFactory(applicationContext);
//		    }
//		}
}
