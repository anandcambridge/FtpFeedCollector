package com.edi.ftp.feedcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FtpFeedCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpFeedCollectorApplication.class, args);
//		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//		scheduler.scheduleAtFixedRate(new LocalFeedCollector(), 0, 5, TimeUnit.SECONDS);
	}
}
