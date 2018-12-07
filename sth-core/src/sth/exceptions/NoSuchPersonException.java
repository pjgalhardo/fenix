package sth.exceptions;

/**
 *
 */
public class NoSuchPersonException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;

  /** Person id. */
  private int _id;

  /**
   * @param id
   */
  public NoSuchPersonException(int id) {
    _id = id;
  }

  @Override
  public String getMessage() {
    return ("NoSuchPersonException");
  }

}
