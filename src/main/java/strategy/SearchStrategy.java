package main.java.strategy;

import java.util.Collection;
import java.util.List;

import main.java.model.Book;

public interface SearchStrategy {
    List<Book> search(Collection<Book> books, String query);
}