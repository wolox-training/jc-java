package wolox.training.security;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wolox.training.exceptions.UserAuthenticationFailException;
import wolox.training.models.User;
import wolox.training.repositories.UsersRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UsersRepository userRepository;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String password = authentication.getCredentials().toString();
		Optional<User> user = Optional
				.ofNullable(userRepository.findByName(authentication.getName())
						.orElseThrow(() -> new UserAuthenticationFailException(
								"User authentication fail", new Exception())));
		if (!encoder.matches(password, user.get().getPassword()))
			return null;
		return new UsernamePasswordAuthenticationToken(user.get().getName(), password, Collections.emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
