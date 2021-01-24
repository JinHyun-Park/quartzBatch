package com.skcc.narubatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.skcc.narubatch.scheduler.SampleScheduler;

@EnableBatchProcessing
@SpringBootApplication
public class NarubatchApplication {
	
	@SuppressWarnings("unused")
	@Autowired
	private SampleScheduler scheduler;

	public static void main(String[] args) {
		SpringApplication.run(NarubatchApplication.class, args);
	}

}
