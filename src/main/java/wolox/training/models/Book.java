package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotOwnedException;

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

	@ManyToMany
	@JsonIgnore
	@JoinColumn(name="book_id")
	private List<User> users= new ArrayList<>();

	public Book() { }

	public Book(final String genre, final String author, final String image, final String title, final String subtitle,
			final String publisher, final String year, final Integer pages, final String isbn) {
		this.genre = genre;
		this.author = author;
		this.image = image;
		this.title = title;
		this.subtitle = subtitle;
		this.publisher = publisher;
		this.year = year;
		this.pages = pages;
		this.isbn = isbn;
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
		Preconditions.checkArgument(pages > 0, "Quantity of pages cannot be negative or zero");
		this.pages = pages;
	}

	public String getIsbn() {
		return this.isbn;
	}
	public void setIsbn(String isbn) {
		Preconditions.checkNotNull(isbn, "Isbn cannot be null");
		this.isbn = isbn;
	}

	public void addUsers(User user) {
		Preconditions.checkNotNull(user, "The user to add cannot be null");
		if (this.users.contains(user))
			throw new BookAlreadyOwnedException("User Already Owned this Book", new Exception());
		this.users.add(user);
	}

	public void removeBook(User user) {
		if (!this.users.contains(user))
			throw new BookNotOwnedException("Book Not Owned By This User", new Exception());
		this.users.remove(user);
	}
}
