📚 Library Management System
A comprehensive Java-based library management system demonstrating Object-Oriented Programming principles, SOLID design patterns, and clean architecture.
🎯 Project Overview
This Library Management System is designed for educational purposes to showcase advanced Java programming concepts including:

Object-Oriented Programming (Encapsulation, Inheritance, Polymorphism, Abstraction)
SOLID Principles implementation
Design Patterns (Strategy, Observer)
Java Collections Framework usage
Clean Code practices

✨ Features
Core Functionality

📖 Book Management: Add, remove, update, and search books
👥 Patron Management: Register and manage library members
🔄 Lending Operations: Check out and return books with validation
📊 Inventory Tracking: Real-time availability and reporting
🔍 Advanced Search: Multiple search strategies (Title, Author, ISBN)
📝 Event Logging: Automatic logging of all library operations

System Capabilities

✅ Input validation and error handling
✅ Borrowing limits and constraints
✅ Interactive console interface
✅ Sample data for immediate testing
✅ Extensible architecture for future enhancements

🏗️ Architecture & Design Patterns
Design Patterns Implemented
1. Strategy Pattern 🎯
javapublic interface SearchStrategy {
    List<Book> search(Collection<Book> books, String query);
}

Purpose: Flexible book searching with interchangeable algorithms
Implementation: TitleSearchStrategy, AuthorSearchStrategy, ISBNSearchStrategy
Benefit: Easy to add new search criteria without modifying existing code

2. Observer Pattern 👀
javapublic interface LibraryEventListener {
    void onLibraryEvent(LibraryEvent event);
}

Purpose: Event-driven architecture for logging and notifications
Implementation: LoggingEventListener for automatic operation logging
Benefit: Decoupled event handling, easy to add new listeners

🚀 Getting Started
Prerequisites

Java Development Kit (JDK) 8+
IDE (IntelliJ IDEA, Eclipse, VS Code) or Command Line

Quick Start

Clone or Download the project
Navigate to the project directory
Compile the Java files:
bashjavac -d . src/main/java/**/*.java

Run the application:
bashjava app.LibraryApplication


IDE Setup
IntelliJ IDEA

Open IntelliJ → New → Project from Existing Sources
Select the project folder
Choose "Create project from existing sources"
Right-click LibraryApplication.java → Run

Eclipse

File → New → Java Project
Uncheck "Use default location" and browse to project folder
Right-click LibraryApplication.java → Run As → Java Application

VS Code

Open project folder in VS Code
Install "Extension Pack for Java"
Open LibraryApplication.java and click Run
