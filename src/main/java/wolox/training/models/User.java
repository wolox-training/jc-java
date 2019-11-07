package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;

@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate localDate;

	@OneToMany(mappedBy = "book")
	private List<Book> books;

	public  User() { }

	public User(final String userName, final String name) {
		this.userName = userName;
		this.name = name;
		this.localDate = LocalDate.now();
		this.books = new ArrayList();
	}

	public long getId() {
		return this.id;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		Preconditions.checkNotNull(userName, "Username cannot be null");
		this.userName = userName;
	}

	public LocalDate getLocalDate() {
		return this.localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		Preconditions.checkNotNull(localDate, "Local Date cannot be null");
		this.localDate = localDate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableCollection(this.books);
	}

	public void setBooks(Book book) {
		Preconditions.checkNotNull(book, "The book to add cannot be null");
		if (this.books.contains(book))
			throw new BookAlreadyOwnedException("Book Already Owned", new Exception());
		this.books.add(book);
	}

	public void removeBook(Book book) {
		if (!this.books.contains(book))
			throw new BookNotOwnedException("Book Not Owned By This User", new Exception());
		this.books.remove(book);
	}

}
