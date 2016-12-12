package com.edi.ftp.feedcollector;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.edi.ftp.feedcollector.config.FileCollectionConfig;

@Component
public class FileCollector {
	Logger log = LoggerFactory.getLogger(FileCollector.class);
	@Autowired
	FileCollectionConfig config;
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public Scheduler  processScheduler() throws SchedulerException {
		System.out.println("hello world");

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//		scheduler.setJobFactory(injector.getInstance(SpringBeanJobFactory.class));
	    
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
	    jobFactory.setApplicationContext(applicationContext);
	    scheduler.setJobFactory(jobFactory);
	    		
		setSchedulerJob(scheduler);
		scheduler.start();

		return scheduler;
	}
	
	private void setSchedulerJob(Scheduler scheduler) {
		Map<String, Map<String, String>> ftpconfiguration = config.getFtpconfiguration();
		ftpconfiguration.forEach((k,v) -> {
			String cronScheduler = v.get("cron.scheduler");
			JobDetail jobDet = JobBuilder.
					newJob(LocalFeedCollectorJob.class).
					withIdentity(k, "group " + k).
					build();

			
			Trigger trigger = TriggerBuilder.
					newTrigger().
					withIdentity(k, "group " + k).
					withSchedule(
							CronScheduleBuilder.cronSchedule(cronScheduler)).
					build();
			
			JobDataMap jobDataMap = trigger.getJobDataMap();
			jobDataMap.put("ENV_CONF", v);
			try {
				scheduler.scheduleJob(jobDet, trigger);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
