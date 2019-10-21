package wolox.training.controllers;
import java.util.List;
import net.bytebuddy.implementation.bytecode.Throw;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Controller
public class BookController {
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@Autowired
	private BookRepository bookRepository;

	@GetMapping
	public Iterable findAll() {
		return bookRepository.findAll();
	}

	@GetMapping("/title/{bookTitle}")
	public Iterable<Book> findByTitle(@PathVariable String bookTitle) {
		return bookRepository.findBookByTitle(bookTitle);
	}

	@GetMapping("/{id}")
	public Book findOne(@PathVariable Long id) {
		return bookRepository.findBookById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book create(@RequestBody Book book) {
		bookRepository.findBookById(book.getId()).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
		return bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Throwable {
		bookRepository.deleteBookById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found", new Exception()));
	}

	@PutMapping("/{id}")
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (book.getId() != id) {
			throw new BookIdMismatchException("Book id mismatch", new Exception());
		}
		return bookRepository.save(book);
	}

}
