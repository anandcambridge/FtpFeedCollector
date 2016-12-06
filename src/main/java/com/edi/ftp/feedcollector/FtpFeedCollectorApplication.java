package com.edi.ftp.feedcollector;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FtpFeedCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpFeedCollectorApplication.class, args);
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new LocalFeedCollector(), 0, 5, TimeUnit.SECONDS);
	}
}
