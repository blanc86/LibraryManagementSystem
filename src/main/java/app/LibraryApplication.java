package main.java.app;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import main.java.model.Book;
import main.java.model.Patron;
import main.java.service.LibraryService;

public class LibraryApplication {
    private static final Logger logger = Logger.getLogger(LibraryApplication.class.getName());
    private LibraryService libraryService;
    private Scanner scanner;
    
    public LibraryApplication() {
        this.libraryService = new LibraryService();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        // Initialize with sample data
        initializeSampleData();
        
        System.out.println("=== Welcome to Library Management System ===");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: addBook(); break;
                case 2: searchBooks(); break;
                case 3: addPatron(); break;
                case 4: checkoutBook(); break;
                case 5: returnBook(); break;
                case 6: viewInventoryReport(); break;
                case 7: viewAllBooks(); break;
                case 8: viewAllPatrons(); break;
                case 0: 
                    System.out.println("Thank you for using Library Management System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Add Book");
        System.out.println("2. Search Books");
        System.out.println("3. Add Patron");
        System.out.println("4. Checkout Book");
        System.out.println("5. Return Book");
        System.out.println("6. View Inventory Report");
        System.out.println("7. View All Books");
        System.out.println("8. View All Patrons");
        System.out.println("0. Exit");
    }
    
    private void addBook() {
        System.out.println("\n=== Add New Book ===");
        String isbn = getStringInput("Enter ISBN: ");
        String title = getStringInput("Enter title: ");
        String author = getStringInput("Enter author: ");
        int year = getIntInput("Enter publication year: ");
        
        Book book = new Book(isbn, title, author, year);
        if (libraryService.addBook(book)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book. ISBN might already exist.");
        }
    }
    
    private void searchBooks() {
        System.out.println("\n=== Search Books ===");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by ISBN");
        
        int choice = getIntInput("Enter search type: ");
        String query = getStringInput("Enter search query: ");
        
        List<Book> results;
        switch (choice) {
            case 1: results = libraryService.searchBooksByTitle(query); break;
            case 2: results = libraryService.searchBooksByAuthor(query); break;
            case 3: results = libraryService.searchBooksByISBN(query); break;
            default:
                System.out.println("Invalid search type.");
                return;
        }
        
        if (results.isEmpty()) {
            System.out.println("No books found matching your query.");
        } else {
            System.out.println("Search Results:");
            results.forEach(System.out::println);
        }
    }
    
    private void addPatron() {
        System.out.println("\n=== Add New Patron ===");
        String patronId = getStringInput("Enter patron ID: ");
        String name = getStringInput("Enter name: ");
        String email = getStringInput("Enter email: ");
        String phone = getStringInput("Enter phone: ");
        
        Patron patron = new Patron(patronId, name, email, phone);
        if (libraryService.addPatron(patron)) {
            System.out.println("Patron added successfully!");
        } else {
            System.out.println("Failed to add patron. ID might already exist.");
        }
    }
    
    private void checkoutBook() {
        System.out.println("\n=== Checkout Book ===");
        String patronId = getStringInput("Enter patron ID: ");
        String isbn = getStringInput("Enter book ISBN: ");
        
        if (libraryService.checkoutBook(patronId, isbn)) {
            System.out.println("Book checked out successfully!");
        } else {
            System.out.println("Failed to checkout book. Check patron ID, ISBN, and availability.");
        }
    }
    
    private void returnBook() {
        System.out.println("\n=== Return Book ===");
        String patronId = getStringInput("Enter patron ID: ");
        String isbn = getStringInput("Enter book ISBN: ");
        
        if (libraryService.returnBook(patronId, isbn)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book. Check patron ID and ISBN.");
        }
    }
    
    private void viewInventoryReport() {
        System.out.println("\n=== Inventory Report ===");
        Map<String, Integer> report = libraryService.getInventoryReport();
        System.out.println("Total Books: " + report.get("total"));
        System.out.println("Available Books: " + report.get("available"));
        System.out.println("Borrowed Books: " + report.get("borrowed"));
    }
    
    private void viewAllBooks() {
        System.out.println("\n=== All Books ===");
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            books.forEach(System.out::println);
        }
    }
    
    private void viewAllPatrons() {
        System.out.println("\n=== All Patrons ===");
        List<Patron> patrons = libraryService.getAllPatrons();
        if (patrons.isEmpty()) {
            System.out.println("No patrons registered.");
        } else {
            patrons.forEach(System.out::println);
        }
    }
    
    private void initializeSampleData() {
        // Add sample books
        libraryService.addBook(new Book("978-0-132-35088-4", "Clean Code", "Robert C. Martin", 2008));
        libraryService.addBook(new Book("978-0-201-61622-4", "The Pragmatic Programmer", "Andrew Hunt", 1999));
        libraryService.addBook(new Book("978-0-596-00784-8", "Head First Design Patterns", "Eric Freeman", 2004));
        
        // Add sample patrons
        libraryService.addPatron(new Patron("P001", "John Doe", "john@email.com", "123-456-7890"));
        libraryService.addPatron(new Patron("P002", "Jane Smith", "jane@email.com", "098-765-4321"));
        
        logger.info("Sample data initialized");
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public static void main(String[] args) {
        new LibraryApplication().run();
    }
}
