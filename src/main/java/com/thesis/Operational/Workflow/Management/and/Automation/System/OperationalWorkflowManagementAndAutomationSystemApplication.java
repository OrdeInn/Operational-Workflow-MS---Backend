package com.thesis.Operational.Workflow.Management.and.Automation.System;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class OperationalWorkflowManagementAndAutomationSystemApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OperationalWorkflowManagementAndAutomationSystemApplication.class, args);
	}

	@PostConstruct
	void started() {

		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Istanbul")));
		log.info("Time Zone Set");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		return builder.sources(OperationalWorkflowManagementAndAutomationSystemApplication.class);
	}
}
