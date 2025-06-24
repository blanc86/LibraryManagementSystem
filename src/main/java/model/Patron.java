package main.java.model;
import java.util.*;
public class Patron {
    private String patronId;
    private String name;
    private String email;
    private String phone;
    private List<String> borrowingHistory;
    private Set<String> currentlyBorrowed;
    private int maxBooksAllowed;
    
    public Patron(String patronId, String name, String email, String phone) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.borrowingHistory = new ArrayList<>();
        this.currentlyBorrowed = new HashSet<>();
        this.maxBooksAllowed = 5; // Default limit
    }
    
    // Getters and setters
    public String getPatronId() { return patronId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<String> getBorrowingHistory() { return new ArrayList<>(borrowingHistory); }
    public Set<String> getCurrentlyBorrowed() { return new HashSet<>(currentlyBorrowed); }
    public int getMaxBooksAllowed() { return maxBooksAllowed; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMaxBooksAllowed(int maxBooks) { this.maxBooksAllowed = maxBooks; }
    
    public boolean canBorrowMore() {
        return currentlyBorrowed.size() < maxBooksAllowed;
    }
    
    public void addToBorrowingHistory(String isbn) {
        borrowingHistory.add(isbn);
    }
    
    public void borrowBook(String isbn) {
        currentlyBorrowed.add(isbn);
        addToBorrowingHistory(isbn);
    }
    
    public void returnBook(String isbn) {
        currentlyBorrowed.remove(isbn);
    }
    
    @Override
    public String toString() {
        return String.format("Patron{ID='%s', name='%s', email='%s', borrowed=%d/%d}",
                patronId, name, email, currentlyBorrowed.size(), maxBooksAllowed);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patron patron = (Patron) obj;
        return patronId.equals(patron.patronId);
    }
    
    @Override
    public int hashCode() {
        return patronId.hashCode();
    }
}