package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@Api
@RequestMapping("/bookApi/")
public class BookController {

	@GetMapping("/greeting")
	@ApiOperation(value = "Test Hello World")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/")
	@ApiOperation(value = "Get all books")
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

	@GetMapping("/title/{bookTitle}")
	@ApiOperation(value = "Find all books with a particular title")
	public Iterable<Book> findAllByTitle(@PathVariable String bookTitle) {
		return bookRepository.findAllByTitle(bookTitle);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find one book for its id")
	public Book findOne(@PathVariable Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create book")
	public Book create(@RequestBody Book book) {
		return bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete book")
	public void delete(@PathVariable Long id) throws Throwable {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
		bookRepository.delete(book);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update book")
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (book.getId() != id) {
			throw new BookIdMismatchException("Book id mismatch", new Exception());
		}
		return bookRepository.save(book);
	}
}
