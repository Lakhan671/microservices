package com.os.micro.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.os.micro.limitsservice.bean.Configuration;
import com.os.micro.limitsservice.bean.LimitsConfiguration;

@RestController
@RefreshScope
public class LimitsConfigurationController {
	@Autowired
	private Configuration configuration;
	@GetMapping(value = "/limit")
	public LimitsConfiguration retriveLimitsConfiguration() {
		return new LimitsConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
	@GetMapping("/fault-tolerance")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	public LimitsConfiguration retrieveConfiguration() {
		throw new RuntimeException("Not available");
	}

	public LimitsConfiguration fallbackRetrieveConfiguration() {
		return new LimitsConfiguration(999, 9);
	}
}
