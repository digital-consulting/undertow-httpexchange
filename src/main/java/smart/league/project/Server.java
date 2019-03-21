package smart.league.project;

import javax.servlet.ServletException;

import smart.league.project.undertow.standalone.UndertowServer;

import static smart.league.project.util.cloud.DeploymentConfiguration.getProperty;

import java.util.concurrent.locks.Condition;


public class Server {

	public static void main(String[] args)
			throws ServletException {

		final String  host = getProperty("undertow.host", "0.0.0.0");
		final Integer port = getProperty("undertow.port", 8081);

		final UndertowServer server = new UndertowServer(host, port, "undertow-httpexchange.war");
		
		final Condition newCondition = server.LOCK.newCondition();
		
		server.start();
		try {
			while( true )
				newCondition.awaitNanos(1);
		} catch ( InterruptedException cause ) {
			server.stop();
		}
	}

}
