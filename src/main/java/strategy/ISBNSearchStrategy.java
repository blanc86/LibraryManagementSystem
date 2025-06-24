package main.java.strategy;

// ISBNSearchStrategy.java
import java.util.*;
import java.util.stream.Collectors;

import main.java.model.Book;

public class ISBNSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(query))
                .collect(Collectors.toList());
    }
}