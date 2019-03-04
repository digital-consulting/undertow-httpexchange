package smart.league.project.server.exchange;

public class Response<T> {

	private int status;
	private T body;
	
	public Response() {
	}
	
	public Response(T body) {
		this.body = body;
	}
	
	public int getStatus() {
		return status;
	}
	
	public T getBody() {
		return body;
	}
	

	public Response<T> status(int status) {
		this.status = status;
		return this;
	}
	
	public Response<T> body(T body) {
		this.body = body;
		return this;
	}

}
