package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND , reason = "Book Id Mismatch")
public class BookIdMismatchException extends RuntimeException {

  public BookIdMismatchException(String message, Throwable cause) {
    super(message, cause);
  }
}
