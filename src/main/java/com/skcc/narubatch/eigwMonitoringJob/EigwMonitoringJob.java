package com.skcc.narubatch.eigwMonitoringJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skcc.narubatch.mapper.eigwdb.EigwMonitoringMapper;
import com.skcc.narubatch.mapper.narudb.NaruMonitoringMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EigwMonitoringJob implements Job {
	
	@Autowired
	EigwMonitoringMapper eigwMonitoringMapper;
	
	@Autowired
	NaruMonitoringMapper naruMonitoringMapper;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MM월 dd일");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH시 mm분 ss초");
		String currentDate = sdf1.format(date);
		String currentTime = sdf2.format(date);
		
		log.info("===========EigwMonitoringJob execute() method Start !! ===========");
		log.info("Start time >>> {}", currentDate + " " + currentTime);
		
		List<Map<String, Object>> eigwList = eigwMonitoringMapper.selectEigwOnlineError();
		log.debug("eigwList count : [{}]", eigwList.size());
		
//		for(Map<String, Object> eigw : eigwList) {
//			naruMonitoringMapper.insertEigwMonOnlineError(eigw);
//		}
		
	}
}
