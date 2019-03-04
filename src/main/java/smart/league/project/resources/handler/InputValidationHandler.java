package smart.league.project.resources.handler;

import smart.league.project.server.exchange.HttpExchange;
import smart.league.project.server.handler.Handler;

public class InputValidationHandler implements Handler<HttpExchange> {

	@Override public void handle(HttpExchange exchange) {
		String param = exchange.getParameter("param");
		if (param == null || "".equals(param)) {
			throw new RuntimeException("Null param");
		}
	}
}
