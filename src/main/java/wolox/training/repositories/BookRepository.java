package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

	public Optional<Book> findBookByAuthor(String author);

	public Iterable<Book> findAll();

	public Iterable<Book> findBookByTitle(String title);

	public Optional<Book> findBookById(long id);

	public Book save(Book book);

	public Optional deleteBookById(long id);

}
