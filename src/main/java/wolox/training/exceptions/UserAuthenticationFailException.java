package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED , reason = "User authentication fail")
public class UserAuthenticationFailException extends RuntimeException {

	public UserAuthenticationFailException(String message, Throwable cause) {
		super(message, cause);
	}
}
