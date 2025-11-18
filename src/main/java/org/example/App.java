package org.example;

import java.util.List;

/**
 * Simple demo application to exercise the Library classes.
 * <p>
 * This is mainly here so that the project has a main method and you can run it.
 * The real point of the assignment is to write unit tests for the behavior
 * of the other classes.
 */
public class App {

    public static void main(String[] args) {
        Library library = new Library();

        // Display some info
        System.out.println("Total books: " + library.getTotalBookCount());
        System.out.println("Total members: " + library.getTotalMemberCount());
        System.out.println("Total borrowed: " + library.getTotalBorrowedBookCount());

        List<Book> available = library.getAvailableBooks();
        System.out.println("Available books: " + available.size());

        double avgPages = LibraryStatistics.calculateAveragePages(library.getAllBooks());
        System.out.println("Average pages: " + avgPages);

        double percentCheckedOut =
                LibraryStatistics.calculateCheckedOutPercentage(library.getAllBooks());
        System.out.println("Checked out %: " + percentCheckedOut);

        // Return a book
        library.returnBook("B2", "M1");

        System.out.println("Books borrowed by Alice: "
                + library.getBooksBorrowedByMember("M1").size());
        System.out.println("Most popular books: " + library.getMostPopularBooks(2));
    }
}
