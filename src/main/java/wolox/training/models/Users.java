package wolox.training.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;

@Entity
public class Users {
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

	public Users() {
		this.books = new List<Book>() {
			@Override
			public int size() {
				return 0;
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public boolean contains(Object o) {
				return false;
			}

			@Override
			public Iterator<Book> iterator() {
				return null;
			}

			@Override
			public Object[] toArray() {
				return new Object[0];
			}

			@Override
			public <T> T[] toArray(T[] ts) {
				return null;
			}

			@Override
			public boolean add(Book book) {
				return false;
			}

			@Override
			public boolean remove(Object o) {
				return false;
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				return false;
			}

			@Override
			public boolean addAll(Collection<? extends Book> collection) {
				return false;
			}

			@Override
			public boolean addAll(int i, Collection<? extends Book> collection) {
				return false;
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				return false;
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				return false;
			}

			@Override
			public void clear() {

			}

			@Override
			public Book get(int i) {
				return null;
			}

			@Override
			public Book set(int i, Book book) {
				return null;
			}

			@Override
			public void add(int i, Book book) {

			}

			@Override
			public Book remove(int i) {
				return null;
			}

			@Override
			public int indexOf(Object o) {
				return 0;
			}

			@Override
			public int lastIndexOf(Object o) {
				return 0;
			}

			@Override
			public ListIterator<Book> listIterator() {
				return null;
			}

			@Override
			public ListIterator<Book> listIterator(int i) {
				return null;
			}

			@Override
			public List<Book> subList(int i, int i1) {
				return null;
			}
		};
	}

	public Users(final String userName, final String name, final LocalDate localDate, final List<Book> books) {
		this.userName = userName;
		this.name = name;
		this.localDate = localDate;
		this.books = books;
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
