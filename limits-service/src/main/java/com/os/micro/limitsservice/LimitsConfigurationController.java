package com.os.micro.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.os.micro.limitsservice.bean.Configuration;
import com.os.micro.limitsservice.bean.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {
	@Autowired
	private Configuration configuration;
	@GetMapping(value = "/limit")
	public LimitsConfiguration retriveLimitsConfiguration() {
		return new LimitsConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
}
