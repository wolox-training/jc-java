package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	private List<User> users;

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
		this.users = new ArrayList();
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
		this.author = author;
	}

	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPublisher() {
		return this.publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public Integer getPages() {
		return this.pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getIsbn() {
		return this.isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<User> getUsers() {
		return (List<User>) Collections.unmodifiableCollection(this.users);
	}
	public void setUsers(User user) {
		this.users.add(user);
	}
}
