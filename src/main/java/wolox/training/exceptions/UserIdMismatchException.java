package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND , reason = "User Id Mismatch")
public class UserIdMismatchException extends RuntimeException {

	public UserIdMismatchException(String message, Throwable cause) {
		super(message, cause);
	}
}
