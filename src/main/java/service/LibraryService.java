package main.java.service;

import java.util.*;
import java.util.logging.Logger;

import main.java.model.Book;
import main.java.model.Patron;
import main.java.observer.LibraryEvent;
import main.java.observer.LibraryEventListener;
import main.java.observer.LoggingEventListener;
import main.java.strategy.AuthorSearchStrategy;
import main.java.strategy.ISBNSearchStrategy;
import main.java.strategy.SearchStrategy;
import main.java.strategy.TitleSearchStrategy;

public class LibraryService {
    private static final Logger logger = Logger.getLogger(LibraryService.class.getName());
    
    private Map<String, Book> bookInventory;
    private Map<String, Patron> patrons;
    private SearchStrategy searchStrategy;
    private List<LibraryEventListener> eventListeners;
    
    public LibraryService() {
        this.bookInventory = new HashMap<>();
        this.patrons = new HashMap<>();
        this.searchStrategy = new TitleSearchStrategy(); // Default strategy
        this.eventListeners = new ArrayList<>();
        
        // Add default logging listener
        addEventListener(new LoggingEventListener());
    }
    
    // Event listener management
    public void addEventListener(LibraryEventListener listener) {
        eventListeners.add(listener);
    }
    
    public void removeEventListener(LibraryEventListener listener) {
        eventListeners.remove(listener);
    }
    
    private void notifyListeners(LibraryEvent event) {
        for (LibraryEventListener listener : eventListeners) {
            listener.onLibraryEvent(event);
        }
    }
    
    // Book management
    public boolean addBook(Book book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            return false;
        }
        
        if (bookInventory.containsKey(book.getIsbn())) {
            logger.warning("Book with ISBN " + book.getIsbn() + " already exists");
            return false;
        }
        
        bookInventory.put(book.getIsbn(), book);
        notifyListeners(new LibraryEvent("BOOK_ADDED", 
                "Book added to inventory", null, book.getIsbn()));
        return true;
    }
    
    public boolean removeBook(String isbn) {
        Book book = bookInventory.get(isbn);
        if (book == null) {
            return false;
        }
        
        if (!book.isAvailable()) {
            logger.warning("Cannot remove book " + isbn + " - currently borrowed");
            return false;
        }
        
        bookInventory.remove(isbn);
        notifyListeners(new LibraryEvent("BOOK_REMOVED", 
                "Book removed from inventory", null, isbn));
        return true;
    }
    
    public boolean updateBook(String isbn, Book updatedBook) {
        if (!bookInventory.containsKey(isbn) || updatedBook == null) {
            return false;
        }
        
        Book existingBook = bookInventory.get(isbn);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        
        notifyListeners(new LibraryEvent("BOOK_UPDATED", 
                "Book information updated", null, isbn));
        return true;
    }
    
    public Book getBook(String isbn) {
        return bookInventory.get(isbn);
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookInventory.values());
    }
    
    public List<Book> getAvailableBooks() {
        return bookInventory.values().stream()
                .filter(Book::isAvailable)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    // Search functionality with Strategy pattern
    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }
    
    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return searchStrategy.search(bookInventory.values(), query);
    }
    
    public List<Book> searchBooksByTitle(String title) {
        setSearchStrategy(new TitleSearchStrategy());
        return searchBooks(title);
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        setSearchStrategy(new AuthorSearchStrategy());
        return searchBooks(author);
    }
    
    public List<Book> searchBooksByISBN(String isbn) {
        setSearchStrategy(new ISBNSearchStrategy());
        return searchBooks(isbn);
    }
    
    // Patron management
    public boolean addPatron(Patron patron) {
        if (patron == null || patron.getPatronId() == null || patron.getPatronId().trim().isEmpty()) {
            return false;
        }
        
        if (patrons.containsKey(patron.getPatronId())) {
            logger.warning("Patron with ID " + patron.getPatronId() + " already exists");
            return false;
        }
        
        patrons.put(patron.getPatronId(), patron);
        notifyListeners(new LibraryEvent("PATRON_ADDED", 
                "New patron registered", patron.getPatronId(), null));
        return true;
    }
    
    public boolean updatePatron(String patronId, Patron updatedPatron) {
        if (!patrons.containsKey(patronId) || updatedPatron == null) {
            return false;
        }
        
        Patron existingPatron = patrons.get(patronId);
        existingPatron.setName(updatedPatron.getName());
        existingPatron.setEmail(updatedPatron.getEmail());
        existingPatron.setPhone(updatedPatron.getPhone());
        
        notifyListeners(new LibraryEvent("PATRON_UPDATED", 
                "Patron information updated", patronId, null));
        return true;
    }
    
    public Patron getPatron(String patronId) {
        return patrons.get(patronId);
    }
    
    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }
    
    // Lending operations
    public boolean checkoutBook(String patronId, String isbn) {
        Patron patron = patrons.get(patronId);
        Book book = bookInventory.get(isbn);
        
        if (patron == null || book == null) {
            logger.warning("Invalid patron ID or ISBN for checkout");
            return false;
        }
        
        if (!book.isAvailable()) {
            logger.warning("Book " + isbn + " is not available");
            return false;
        }
        
        if (!patron.canBorrowMore()) {
            logger.warning("Patron " + patronId + " has reached borrowing limit");
            return false;
        }
        
        book.setAvailable(false);
        patron.borrowBook(isbn);
        
        notifyListeners(new LibraryEvent("BOOK_CHECKED_OUT", 
                "Book checked out", patronId, isbn));
        
        return true;
    }
    
    public boolean returnBook(String patronId, String isbn) {
        Patron patron = patrons.get(patronId);
        Book book = bookInventory.get(isbn);
        
        if (patron == null || book == null) {
            logger.warning("Invalid patron ID or ISBN for return");
            return false;
        }
        
        if (!patron.getCurrentlyBorrowed().contains(isbn)) {
            logger.warning("Patron " + patronId + " has not borrowed book " + isbn);
            return false;
        }
        
        book.setAvailable(true);
        patron.returnBook(isbn);
        
        notifyListeners(new LibraryEvent("BOOK_RETURNED", 
                "Book returned", patronId, isbn));
        
        return true;
    }
    
    // Inventory reporting
    public Map<String, Integer> getInventoryReport() {
        Map<String, Integer> report = new HashMap<>();
        long totalBooks = bookInventory.size();
        long availableBooks = bookInventory.values().stream()
                .mapToLong(book -> book.isAvailable() ? 1 : 0)
                .sum();
        long borrowedBooks = totalBooks - availableBooks;
        
        report.put("total", (int) totalBooks);
        report.put("available", (int) availableBooks);
        report.put("borrowed", (int) borrowedBooks);
        
        return report;
    }
    
    public List<Book> getBorrowedBooks() {
        return bookInventory.values().stream()
                .filter(book -> !book.isAvailable())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}