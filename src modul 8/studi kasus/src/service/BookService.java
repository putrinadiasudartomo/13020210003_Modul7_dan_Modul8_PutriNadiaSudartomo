package service;

import java.util.List;
import model.Book;

public interface BookService {

    public void addBook(Book book);

    public List<Book> findBookList();

    public Book findBookById(int id);

    public void updateBook(int id, Book book);

    public void removeBook(int id);
}
