package service;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookServiceImpl implements BookService {

    private Connection connection = Database.startConnection();
    private PreparedStatement statement;

    @Override
    public void addBook(Book book) {
        try {
            String query = "INSERT INTO books (title, author_name) VALUES (?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthorName());
            statement.executeUpdate();

            System.out.println("Book has been added!\n");
            statement.close();
        } catch (SQLException exc) {
            System.out.println("FAILED TO ADD BOOK " + exc.getMessage());
        }
    }

    @Override
    public List<Book> findBookList() {
        List<Book> books = new ArrayList<>();

        try {

            String query = "SELECT * FROM books";
            statement = (PreparedStatement) connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setId(result.getInt("id"));
                book.setTitle(result.getString("title"));
                book.setAuthorName(result.getString("author_name"));

                books.add(book);
            }
            statement.close();
            return books;
        } catch (SQLException exc) {
            System.out.println("FAILED TO GET BOOK LIST: " + exc.getMessage());
        }
        return books;
    }

    @Override
    public Book findBookById(int id) {
        Book book = new Book();
        try {

            String query = "SELECT * FROM books WHERE id = ?";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int bookId = result.getInt("id");
                String title = result.getString("title");
                String authorName = result.getString("author_name");

                book.setId(id);
                book.setTitle(title);
                book.setAuthorName(authorName);

            } else {
                return null;
            }
            statement.close();
            return book;
        } catch (SQLException exc) {
            System.out.println("FAILED TO GET BOOK : " + exc.getMessage());
        }
        return book;
    }

    @Override
    public void updateBook(int id, Book book) {
        try {

            String query = "UPDATE books SET title = ?, author_name = ? WHERE id = ?";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthorName());
            statement.setInt(3, id);
            statement.executeUpdate();

            System.out.println("Successfully updated the book with id = " + id);
            System.out.println("\n");
            statement.close();
        } catch (SQLException exc) {
            System.out.println("FAILED TO UPDATE BOOK DATA : " + exc.getMessage());
        }
    }

    @Override
    public void removeBook(int id) {
        try {

            String query = "DELETE FROM books WHERE id = ?";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            System.out.println("Successfully delete book!\n");
            statement.close();
        } catch (SQLException exc) {
            System.out.println("FAILED TO DELETE BOOK DATA : " + exc.getMessage());
        }
    }
}
