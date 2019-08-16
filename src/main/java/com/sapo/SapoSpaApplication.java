package com.sapo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class SapoSpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SapoSpaApplication.class, args);
	}

//	@Bean
//	public TaskScheduler taskScheduler() {
//		final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//		scheduler.setPoolSize(10);
//		return scheduler;
//	}

}
