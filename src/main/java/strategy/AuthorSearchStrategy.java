package main.java.strategy;

// AuthorSearchStrategy.java
import java.util.*;
import java.util.stream.Collectors;

import main.java.model.Book;

public class AuthorSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}