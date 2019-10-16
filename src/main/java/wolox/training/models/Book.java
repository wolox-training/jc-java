package wolox.training.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;

public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	@Column()
	private String Genre;

	@Column(nullable = false)
	private String Author;

	@Column(nullable = false)
	private String Image;

	@Column(nullable = false)
	private String Title;

	@Column(nullable = false)
	private String Subtitle;

	@Column(nullable = false)
	private String Publisher;

	@Column(nullable = false)
	private String Year;

	@Column(nullable = false)
	private Integer Pages;

	@Column(nullable = false)
	private String Isbn;

	public Book() { }

	public Book(String genre, String author, String image, String title, String subtitle,
			String publisher, String year, Integer pages, String isbn) {
		this.Genre = genre;
		this.Author = author;
		this.Image = image;
		this.Title = title;
		this.Subtitle = subtitle;
		this.Publisher = publisher;
		this.Year = year;
		this.Pages = pages;
		this.Isbn = isbn;
	}

	public String getGenre() {
		return this.Genre;
	}
	public void setGenre(String genre) {
		this.Genre = genre;
	}

	public String getAuthor() {
		return this.Author;
	}
	public void setAuthor(String author) {
		this.Author = author;
	}

	public String getImage() {
		return this.Image;
	}
	public void setImage(String image) {
		this.Image = image;
	}

	public String getTitle() {
		return this.Title;
	}
	public void setTitle(String title) {
		this.Title = title;
	}

	public String getSubtitle() {
		return this.Subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.Subtitle = subtitle;
	}

	public String getPublisher() {
		return this.Publisher;
	}
	public void setPublisher(String publisher) {
		this.Publisher = publisher;
	}

	public String getYear() {
		return this.Year;
	}
	public void setYear(String year) {
		this.Year = year;
	}

	public Integer getPages() {
		return this.Pages;
	}
	public void setPages(Integer pages) {
		this.Pages = pages;
	}

	public String getIsbn() {
		return this.Isbn;
	}
	public void setIsbn(String isbn) {
		this.Isbn = isbn;
	}
}