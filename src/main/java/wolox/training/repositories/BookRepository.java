package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	public Optional<Book> findByAuthor(String author);

	public Iterable<Book> findAll();

	public Optional<Iterable<Book>> findByTitle(String title);

	public Optional<Book> findById(Long id);

	public Book save(Book book);

	public void deleteById(Long id);
}
