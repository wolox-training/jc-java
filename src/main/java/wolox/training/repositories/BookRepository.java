package wolox.training.repositories;


import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

	public Optional<Book> findBookByAuthor(String author);
}
