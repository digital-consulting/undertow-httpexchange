package com.api.resources;

import com.api.resources.handler.InputValidationHandler;
import com.api.resources.handler.PalindromeHandler;
import com.async.support.Computation;
import com.async.support.ExecutorsProvider;
import com.server.exchange.HttpExchange;
import com.server.exchange.RequestHandler;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;

@RequestScoped @Path("/") public class TestEndpointRouter {

	@Inject private PalindromeHandler palindromeHandler;

	@Inject private InputValidationHandler inputValidationHandler;

	@Inject private ExecutorsProvider executorsProvider;

	@GET
	@Path("test") public void test(@QueryParam("param") String param, @Suspended AsyncResponse asyncResponse) {
		HttpExchange exchange = new HttpExchange().addQueryParam("param", param);

		RequestHandler requestHandler = RequestHandler.route("/test")
				.handler(inputValidationHandler)
				.handler(palindromeHandler);

		ExecutorService executorService = executorsProvider.getExecutorService();
		Computation.computeAsync(() -> handleRequest(requestHandler, exchange), executorService)
				.thenApplyAsync(asyncResponse::resume, executorService)
				.exceptionally(error -> asyncResponse.resume(handleException(error)));
	}

	private Response handleRequest(RequestHandler router, HttpExchange exchange) {
		try {
			router.handle(exchange);
			return Response.ok(exchange.getResponse().getBody()).build();
		} catch (Exception e) {
			return Response.status(400).build();
		}
	}

	private Response handleException(Throwable ex) {
		return Response.serverError().entity(ex).build();
	}

}
