package main.java.observer;

public class LibraryEvent {
    private String eventType;
    private String description;
    private String patronId;
    private String bookIsbn;
    private long timestamp;
    
    public LibraryEvent(String eventType, String description, String patronId, String bookIsbn) {
        this.eventType = eventType;
        this.description = description;
        this.patronId = patronId;
        this.bookIsbn = bookIsbn;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters
    public String getEventType() { return eventType; }
    public String getDescription() { return description; }
    public String getPatronId() { return patronId; }
    public String getBookIsbn() { return bookIsbn; }
    public long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - Patron: %s, Book: %s", 
                eventType, description, patronId, bookIsbn);
    }
}
