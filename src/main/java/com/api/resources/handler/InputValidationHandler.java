package com.api.resources.handler;

import com.server.exchange.HttpExchange;
import com.server.handler.Handler;

public class InputValidationHandler implements Handler<HttpExchange> {

	@Override public void handle(HttpExchange exchange) {
		String param = exchange.getParameter("param");
		if (param == null || "".equals(param)) {
			throw new RuntimeException("Null param");
		}
	}
}
