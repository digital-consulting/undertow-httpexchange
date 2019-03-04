package smart.league.project.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import smart.league.project.resources.handler.InputValidationHandler;
import smart.league.project.resources.handler.PalindromeHandler;
import smart.league.project.server.exchange.HttpExchange;
import smart.league.project.server.exchange.RequestHandler;
import smart.league.project.util.async.Computation;
import smart.league.project.util.async.ExecutorsProvider;
import smart.league.project.util.web.json.JsonResponse;

import java.util.concurrent.ExecutorService;

@RequestScoped
@Path("/")
@Produces({"application/json", "application/xml"})
public class TestEndpointRouter {

	@Inject 
	private PalindromeHandler palindromeHandler;

	@Inject 
	private InputValidationHandler inputValidationHandler;

	@Inject 
	private ExecutorsProvider executorsProvider;

	@GET
	@Path("test") 
	public void test(@QueryParam("param") String param, @Suspended AsyncResponse asyncResponse) {

		HttpExchange exchange = new HttpExchange()
				.addQueryParam("param", param);

		RequestHandler requestHandler = RequestHandler.route("/test")
				.handler(inputValidationHandler)
				.handler(palindromeHandler);

		ExecutorService executorService = executorsProvider.getExecutorService();
		Computation.computeAsync(() -> handleRequest(requestHandler, exchange), executorService)
		    .thenApplyAsync(asyncResponse::resume, executorService)
		    .exceptionally(error -> asyncResponse.resume(handleException(error)));
	}

	private Response handleRequest(RequestHandler requestHandler, HttpExchange exchange) {
		requestHandler.handle(exchange);
		return Response.ok(exchange.getResponse().getBody()).build();

	}

	private Response handleException(Throwable ex) {
		JsonResponse jsonResponse  = new JsonResponse()
				.with("Status", "INTERNAL_SERVER_ERROR")
				.with("Code", 500)
				.with("Exception", ex)
				.done();
		
		return Response.serverError().entity(jsonResponse).build();
	}

}
