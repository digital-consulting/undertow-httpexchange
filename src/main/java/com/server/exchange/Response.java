package com.server.exchange;

public class Response<T> {

	private int status;
	private T body;

	public int getStatus() {
		return status;
	}

	public Response<T> status(int status) {
		this.status = status;
		return this;
	}

	public T getBody() {
		return body;
	}

	public Response<T> body(T body) {
		this.body = body;
		return this;
	}

	public Response() {
	}
}
