package com.api.resources.handler;

import com.server.exchange.HttpExchange;
import com.server.handler.Handler;

import javax.inject.Inject;

public class PalindromeHandler implements Handler<HttpExchange> {

	public PalindromeHandler(PalindromeService palindromeService) {
		this.palindromeService = palindromeService;
	}

	public PalindromeHandler() {
	}

	@Inject
	private PalindromeService palindromeService;

	@Override public void handle(HttpExchange exchange) {
		String param = exchange.getParameter("param");
		boolean isPalindrome = palindromeService.isPalindrome(param);
		if (isPalindrome) {
			exchange.setResponse(new Response<String>().body("The \"" + param + "\" parameter is palindrome!"));
		} else {
			exchange.setResponse(new Response<String>().body("The \"" + param + "\" parameter isn't a palindrome!"));
		}
	}
}
