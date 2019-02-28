package com.server.bean.producer;

import com.api.resources.handler.InputValidationHandler;
import com.api.resources.handler.PalindromeHandler;
import com.service.PalindromeService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class BeanProducer {

	@Inject
	private PalindromeService palindromeService;

	@Produces
	@ApplicationScoped
	public PalindromeService producePalindromService() {
		return new PalindromeService();
	}

	@Produces
	@ApplicationScoped
	public PalindromeHandler producePalindromHandler() {
		return new PalindromeHandler(palindromeService);
	}

	@Produces
	@ApplicationScoped
	public InputValidationHandler produceInputValidationHandler() {
		return new InputValidationHandler();
	}
}
