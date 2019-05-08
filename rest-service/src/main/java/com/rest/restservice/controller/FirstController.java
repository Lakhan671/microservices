package com.rest.restservice.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.rest.restservice.controller.model.HelloWordBean;

@RestController
public class FirstController {
	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = "/helloword")
	//@GetMapping(value = "/helloword/{param}")
	public String helloWord(//@PathVariable String param
	@RequestHeader(name="Accept_Languale",required=false) Locale locale) {

		return "Hello " + messageSource.getMessage("good.morning.message",null, locale);

	}

	@GetMapping(value = "/hellowordBean/{param}")
	public HelloWordBean helloWordBean(@PathVariable String param) {
		return new HelloWordBean(param);

	}
}
