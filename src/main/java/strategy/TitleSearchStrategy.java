package main.java.strategy;

import java.util.*;
import java.util.stream.Collectors;

import main.java.model.Book;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}