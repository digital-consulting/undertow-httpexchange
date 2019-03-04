package smart.league.project.config.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import smart.league.project.resources.handler.InputValidationHandler;
import smart.league.project.resources.handler.PalindromeHandler;
import smart.league.project.service.PalindromeService;

@ApplicationScoped
public class BeanProducer {


	@Produces
	@ApplicationScoped
	public PalindromeService producePalindromService() {
		return new PalindromeService();
	}

	@Produces
	@ApplicationScoped
	public PalindromeHandler producePalindromHandler(PalindromeService palindromeService) {
		return new PalindromeHandler(palindromeService);
	}

	@Produces
	@ApplicationScoped
	public InputValidationHandler produceInputValidationHandler() {
		return new InputValidationHandler();
	}
}
