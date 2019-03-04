package smart.league.project.resources.handler;

import javax.inject.Inject;

import smart.league.project.server.exchange.HttpExchange;
import smart.league.project.server.handler.Handler;
import smart.league.project.service.PalindromeService;
import smart.league.project.util.web.json.JsonResponse;

public class PalindromeHandler implements Handler<HttpExchange> {

	
	private PalindromeService palindromeService;
	
	public PalindromeHandler() {
	}

	@Inject
	public PalindromeHandler(PalindromeService palindromeService) {
		this.palindromeService = palindromeService;
	}



	@Override 
	public void handle(HttpExchange exchange) {
		final String param = exchange.getParameter("param");
		final boolean isPalindrome = palindromeService.isPalindrome(param);
		JsonResponse json = new JsonResponse();
		if (isPalindrome) {
			exchange.response(json.with("Parameter", param).with("Is palindrome", true));
		} else {
			exchange.response(json.with("Parameter", param).with("Is palindrome", false));
		}
	}
}
