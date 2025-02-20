package wolox.training.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserAlreadyExistException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UsersNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;

@RestController
@Api
@RequestMapping("/userApi/")
public class UserController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/")
	public Iterable<User> findAll() {
		return usersRepository.findAll();
	}

	@GetMapping("/name/{usersName}")
	public Iterable<User> findAllByName(@PathVariable String name) {
		return usersRepository.findAllByName(name);
	}

	@GetMapping("/{id}")
	public User findById(@PathVariable Long id) {
		return usersRepository.findById(id).orElseThrow(() -> new UsersNotFoundException("User Not Found", new Exception()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/")
	public User create(@RequestBody User user) {
		if(usersRepository.findById(user.getId()) == null)
			throw new UserAlreadyExistException("User already exist", new Exception());
		return usersRepository.save(user);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Throwable {
		User user = usersRepository.findById(id).orElseThrow(() -> new UsersNotFoundException("User Not Found", new Exception()));
		usersRepository.delete(user);
	}

	@PutMapping("/{id}")
	public User update(@RequestBody User user, @PathVariable Long id) {
		if (!id.equals(user.getId())) {
			throw new UserIdMismatchException("User id mismatch", new Exception());
		}
		return usersRepository.save(user);
	}
	@PutMapping("/{userId}/{bookId}")
	public void addBooks(@PathVariable Long userId, @PathVariable Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
		User user = usersRepository.findById(userId).orElseThrow(() -> new UsersNotFoundException("User Not Found", new Exception()));
		user.addBook(book);
		usersRepository.save(user);
	}

	@DeleteMapping("/{userId}/{bookId}")
	public void removeBook(@PathVariable Long userId, @PathVariable Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
		User user = usersRepository.findById(userId).orElseThrow(() -> new UsersNotFoundException("User Not Found", new Exception()));
		user.removeBook(book);
		usersRepository.save(user);
	}
}
