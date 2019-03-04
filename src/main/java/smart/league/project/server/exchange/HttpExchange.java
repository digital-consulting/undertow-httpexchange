package smart.league.project.server.exchange;

import java.util.LinkedHashMap;
import java.util.Map;

import smart.league.project.util.map.MultiValueMap;

/**
 * An HTTP request/response exchange. A new instance is created for each HTTP request that is
 * received.
 *
 * @author Razvan Prichici
 */
public final class HttpExchange {
	private static final RuntimePermission SET_SECURITY_CONTEXT = new RuntimePermission(
			"digital-consulting.SET_SECURITY_CONTEXT");

	private MultiValueMap<String, String> pathParameters;
	private MultiValueMap<String, String> queryParameters;
	private SecurityContext securityContext;
	private Map<String, Object> properties;

	@SuppressWarnings("rawtypes")
	private Response response;

	/**
	 * Returns the property with the given name registered in the current request/response exchange
	 */
	public Object getProperty(String name) {
		return properties.get(name);
	}

	/**
	 * Binds an object to a given property name in the current request/response exchange.
	 * <p>
	 * A property allows handlers to exchange additional information.
	 * </p>
	 *
	 */
	public void setProperty(String name, Object object) {
		if (properties == null) {
			properties = new LinkedHashMap<>();
		}
		properties.put(name, object);
	}

	/**
	 * Removes a property with the given name from the current request/response exchange
	 */
	public void removeProperty(String name) {
		if (properties == null) {
			return;
		}
		properties.remove(name);
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(SET_SECURITY_CONTEXT);
		}
		this.securityContext = securityContext;
	}

	public MultiValueMap<String, String> getPathParameters() {
		if (pathParameters == null) {
			pathParameters = new MultiValueMap<>();
		}
		return pathParameters;
	}

	public HttpExchange addPathParam(final String name, final String param) {
		if (pathParameters == null) {
			pathParameters = new MultiValueMap<>();
		}

		pathParameters.add(name, param);
		return this;
	}

	public MultiValueMap<String, String> getQueryParameters() {
		if (queryParameters == null) {
			queryParameters = new MultiValueMap<>();
		}
		return queryParameters;
	}

	public HttpExchange addQueryParam(final String name, final String param) {
		if (queryParameters == null) {
			queryParameters = new MultiValueMap<>();
		}

		queryParameters.add(name, param);
		return this;
	}

	public HttpExchange addQueryParam(final String name, final String[] params) {
		if (queryParameters == null) {
			queryParameters = new MultiValueMap<>();
		}
		queryParameters.add(name, params);
		return this;
	}

	public String getParameter(final String name) {
		return getQueryParameters().getFirst(name);
	}

	@SuppressWarnings("unchecked")
	public <T> Response<T> getResponse() {
		return response;
	}

	@SuppressWarnings("unchecked")
	public <T> Response<T> response() {
		response =  new Response<T>();
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Response<T> response(T body) {
		response =  new Response<T>(body);
		return response;
	}
}
