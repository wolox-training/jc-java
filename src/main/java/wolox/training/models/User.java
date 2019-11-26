package wolox.training.models;

import static org.checkerframework.checker.units.UnitsTools.mm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModelProperty;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "book_user",
			joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id",
					referencedColumnName = "id"))
	private List<Book> books = new ArrayList<Book>();

	@Column(nullable = false)
	private String password;

	public  User() { }

	public User(final String userName, final String name, final LocalDate birthDate) {
		this.userName = userName;
		this.name = name;
		this.birthDate = birthDate;
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

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		Preconditions.checkNotNull(name, "Name cannot be null");
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		Preconditions.checkNotNull(birthDate, "Birth Date cannot be null");
		this.birthDate = birthDate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableList(this.books);
	}

	public void addBook(Book book) {
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

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		Preconditions.checkArgument(password != null && !password.isEmpty());
		this.password = password;
	}

}
