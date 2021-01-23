package com.skcc.narubatch.sampleJob2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleJob2 implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MM월 dd일");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH시 mm분 ss초");
		String currentDate = sdf1.format(date);
		String currentTime = sdf2.format(date);
		
		log.info("===========SampleJob222222222 execute() method Start !! ===========");
		log.info("Start time >>> {}", currentDate + " " + currentTime);
	}
}
