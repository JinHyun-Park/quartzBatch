package com.skcc.narubatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.skcc.narubatch.sampleJob.SampleScheduler;
import com.skcc.narubatch.sampleJob2.SampleScheduler2;

@SpringBootApplication
public class NarubatchApplication {
	
	@SuppressWarnings("unused")
	@Autowired
	private SampleScheduler scheduler;
	
	@SuppressWarnings("unused")
	@Autowired
	private SampleScheduler2 schduler2;

	public static void main(String[] args) {
		SpringApplication.run(NarubatchApplication.class, args);
	}

}
