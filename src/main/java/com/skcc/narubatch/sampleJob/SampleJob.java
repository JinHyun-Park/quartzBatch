package com.skcc.narubatch.sampleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SampleJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MM월 dd일");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH시 mm분 ss초");
		String currentDate = sdf1.format(date);
		String currentTime = sdf2.format(date);
		
		log.info("===========11111111111111111 execute() method Start !! ===========");
		log.info("Start time >>> {}", currentDate + " " + currentTime);
	}
}
