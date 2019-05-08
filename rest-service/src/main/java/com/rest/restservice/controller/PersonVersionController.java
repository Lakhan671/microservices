package com.rest.restservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.restservice.controller.model.Name;
import com.rest.restservice.controller.model.PersonV1;
import com.rest.restservice.controller.model.PersonV2;

@RestController
public class PersonVersionController {
	@GetMapping(value="v1/person")
	public PersonV1 personV1() {
		return new PersonV1("lakhan Singh");
	}
	@GetMapping(value="v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Lakhan ","Singh"));
	}
	@GetMapping(value="param/person",params="verson=1")
	public PersonV1 paramV1() {
		return new PersonV1("lakhan Singh");
	}
	@GetMapping(value="param/person",params="verson=2")
	public PersonV2 paramV2() {
		return new PersonV2(new Name("Lakhan ","Singh"));
	}
	
	@GetMapping(value="header/person",headers="X-API-VERSON=1")
	public PersonV1 headerV1() {
		return new PersonV1("lakhan Singh");
	}
	@GetMapping(value="header/person",headers="X-API-VERSON=2")
	public PersonV2 headerV2() {
		return new PersonV2(new Name("Lakhan ","Singh"));
	}
	@GetMapping(value="produces/person",produces="application/biz.company.app-v1+json")
	public PersonV1 producesV1() {
		return new PersonV1("lakhan Singh");
	}
	@GetMapping(value="produces/person",produces="application/biz.company.app-v2+json")
	public PersonV2 producesV2() {
		return new PersonV2(new Name("Lakhan ","Singh"));
	}
}
