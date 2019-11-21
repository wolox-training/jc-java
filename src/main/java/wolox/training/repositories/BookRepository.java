package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	public Optional<Book> findByAuthor(String author);

	public Iterable<Book> findAllByTitle(String title);

	public Optional<Book> findFirstByIsbn(String isbn);

	public Optional<Book> findFirstByPublisherAndGenreAndYear(String publisher, String genre, String year);

}
