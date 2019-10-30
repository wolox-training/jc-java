package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@ApiModel
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	@ApiModelProperty
	private String genre;

	@Column(nullable = false)
	@ApiModelProperty
	private String author;

	@Column(nullable = false)
	@ApiModelProperty
	private String image;

	@Column(nullable = false)
	@ApiModelProperty
	private String title;

	@Column(nullable = false)
	@ApiModelProperty
	private String subtitle;

	@Column(nullable = false)
	@ApiModelProperty
	private String publisher;

	@Column(nullable = false)
	@ApiModelProperty
	private String year;

	@Column(nullable = false)
	@ApiModelProperty
	private Integer pages;

	@Column(nullable = false)
	@ApiModelProperty
	private String isbn;

	@ManyToMany(mappedBy = "books")
	@ApiModelProperty
	private List<Users> users;

	public Book() {
		this.users = new List<Users>() {
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
			public Iterator<Users> iterator() {
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
			public boolean add(Users users) {
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
			public boolean addAll(Collection<? extends Users> collection) {
				return false;
			}

			@Override
			public boolean addAll(int i, Collection<? extends Users> collection) {
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
			public Users get(int i) {
				return null;
			}

			@Override
			public Users set(int i, Users users) {
				return null;
			}

			@Override
			public void add(int i, Users users) {

			}

			@Override
			public Users remove(int i) {
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
			public ListIterator<Users> listIterator() {
				return null;
			}

			@Override
			public ListIterator<Users> listIterator(int i) {
				return null;
			}

			@Override
			public List<Users> subList(int i, int i1) {
				return null;
			}
		};
	}

	public Book(final String genre, final String author, final String image, final String title, final String subtitle,
			final String publisher, final String year, final Integer pages, final String isbn, final List<Users> users) {
		this.genre = genre;
		this.author = author;
		this.image = image;
		this.title = title;
		this.subtitle = subtitle;
		this.publisher = publisher;
		this.year = year;
		this.pages = pages;
		this.isbn = isbn;
		this.users = users;
	}

	public long getId() {
		return this.id;
	}

	public String getGenre() {
		return this.genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return this.author;
	}
	public void setAuthor(String author) {
		Preconditions.checkNotNull(author, "Author cannot be null");
		this.author = author;
	}

	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		Preconditions.checkNotNull(image, "Image cannot be null");
		this.image = image;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		Preconditions.checkNotNull(title, "Title cannot be null");
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}
	public void setSubtitle(String subtitle) {
		Preconditions.checkNotNull(subtitle, "Subtitle cannot be null");
		this.subtitle = subtitle;
	}

	public String getPublisher() {
		return this.publisher;
	}
	public void setPublisher(String publisher) {
		Preconditions.checkNotNull(publisher, "Publisher cannot be null");
		this.publisher = publisher;
	}

	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		Preconditions.checkNotNull(year, "Year cannot be null");
		this.year = year;
	}

	public Integer getPages() {
		return this.pages;
	}
	public void setPages(Integer pages) {
		Preconditions.checkNotNull(pages, "Pages cannot be null");
		Preconditions.checkArgument(pages < 0, "Quantity of pages cannot be negative or zero");
		this.pages = pages;
	}

	public String getIsbn() {
		return this.isbn;
	}
	public void setIsbn(String isbn) {
		Preconditions.checkNotNull(isbn);
		this.isbn = isbn;
	}

	public List<Users> getUsers() {
		return (List<Users>) Collections.unmodifiableCollection(this.users);
	}
	public void setUsers(Users user) {
		Preconditions.checkNotNull(user, "The user to add cannot be null");
		this.users.add(user);
	}
}
