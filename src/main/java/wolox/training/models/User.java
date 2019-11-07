package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "book_users",
			joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "users_id",
					referencedColumnName = "id"))
	private List<Book> books;

	public  User() { }

	public User(final String userName, final String name, final LocalDate localDate) {
		this.userName = userName;
		this.name = name;
		this.localDate = localDate;
		this.books = new ArrayList();
	}

	public long getId() {
		return this.id;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getLocalDate() {
		return this.localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableCollection(this.books);
	}

	public void setBooks(Book book) {
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
