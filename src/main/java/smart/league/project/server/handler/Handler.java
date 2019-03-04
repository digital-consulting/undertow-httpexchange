package smart.league.project.server.handler;

/**
 *  A generic handler.
 */
@FunctionalInterface
public interface Handler<E> {

  /**
   * Something has happened, so tackle it.
   *
   */
  void handle(E event);
}