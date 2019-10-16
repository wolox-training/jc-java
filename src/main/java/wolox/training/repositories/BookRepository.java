package wolox.training.repositories;

import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

public interface BookRepository extends Repository {

	public Book findBookByAuthor(String author);
}
