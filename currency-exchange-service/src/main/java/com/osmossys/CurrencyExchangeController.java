package com.osmossys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	@Autowired
	private Environment environment;

	@GetMapping(value = "currency-exchange/from/{from}/to/{to}")
	public ExchangeValue exhangeApi(@PathVariable String from, @PathVariable String to) {
		//ExchangeValue exchangeValue = new ExchangeValue(1, from, to, BigDecimal.valueOf(876));
		
		ExchangeValue exchangeValue=exchangeValueRepository.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
}
