package wolox.training.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;

public class OpenLibraryService {

	// Base URL of the API
	private static String _baseUrl = "https://openlibrary.org/api/books?bibkeys=";
	// The parameters needed in the url
	// To learn more about the parameters: https://openlibrary.org/dev/docs/api/books
	private static String _params = "&format=json&jscmd=data";
	// ISBN parameter (name) in the url
	private static String _isbnParam = "ISBN:";
	// GET parameter (name) in the url
	private static String _getMethod = "GET";

	public static Book bookInfo(String isbn) {
		try {
			String response = getOpenLibraryResponse(isbn);
			return parse(response, isbn);
		} catch (Exception e) {
			throw new BookNotFoundException("Book Not Found", new Exception());
		}
	}
	private static String getOpenLibraryResponse(String isbn) throws IOException {
		// Generate the URL of the API (using the statics vars and the isbn to search)
		String urlString = new StringBuilder().append(_baseUrl).append(_isbnParam).append(isbn).append(_params).toString();
		// Open the connection to urlString
		HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
		// Set the request method to perform
		connection.setRequestMethod(_getMethod);

		// Some Data

		// InputStreamReader:
		// An InputStreamReader is a bridge from byte streams to character streams:
		// It reads bytes and decodes them into characters using a specified charset.
		// The charset that it uses may be specified by name or may be given explicitly, or the platform's default charset may be accepted.
		// Each invocation of one of an InputStreamReader's read() methods may cause one or more bytes to be read from the underlying byte-input stream.
		// To enable the efficient conversion of bytes to characters,
		// more bytes may be read ahead from the underlying stream than are necessary to satisfy the current read operation.

		// BufferedReader:
		// Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
		// The buffer size may be specified, or the default size may be used. The default is large enough for most purposes.
		// In general, each read request made of a Reader causes a corresponding read request to be made of the underlying character or byte stream.
		// It is therefore advisable to wrap a BufferedReader around any Reader whose read() operations may be costly, such as FileReaders and InputStreamReaders.

		BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		// The StringBuilder class provides no guarantee of synchronization whereas the StringBuffer class does.
		// Instances of StringBuilder are not safe for use by multiple threads.

		StringBuilder json = new StringBuilder();
		String line;
		while ((line = buffer.readLine()) != null)
			json.append(line);
		return json.toString();
	}
	private static Book parse(String response, String isbn) throws IOException {
		JsonNode rootNode = new ObjectMapper().readTree(response);
		JsonNode values = rootNode.findValue(new StringBuilder().append(_isbnParam).append(isbn).toString());
		Book book = new Book();
		book.setIsbn(isbn);
		if (values.has("title"))
			book.setTitle(values.get("title").asText());
		if (values.has("subtitle"))
			book.setSubtitle(values.get("subtitle").asText());
		if (values.has("publish_date"))
			book.setYear(values.get("publish_date").asText());
		if (values.has("number_of_pages"))
			book.setPages(values.get("number_of_pages").asInt());
		if (values.has("publishers"))
			book.setPublisher(values.get("publishers").asText());
		// Convert the list of authors to String (because I don't have a list of they in the model) separated by commas
		// Another option would be to take only the first element of the list.
		if (values.has("authors"))
			book.setAuthor(convertListToString(values.get("authors")));
		return book;
	}
	private static String convertListToString(JsonNode jsonNode) {
		Iterator<JsonNode> elements = jsonNode.elements();
		StringBuilder stringBuilder = new StringBuilder();
		while (elements.hasNext()) {
			JsonNode element = elements.next();
			stringBuilder.append(element.get("name").asText());
			stringBuilder.append(", ");
		}
		// Remove ", " those two characters at the end of the string (in the last append).
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		return stringBuilder.toString();
	}
}
