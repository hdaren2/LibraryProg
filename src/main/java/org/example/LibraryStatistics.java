package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class with static methods for computing statistics on collections of books.
 */
public final class LibraryStatistics {

    private LibraryStatistics() {
        // utility class; prevent instantiation
    }

    /**
     * Calculates the total number of pages across all books.
     */
    public static int countTotalPages(Collection<Book> books) {
        int total = 0;
        if (books == null) {
            return 0;
        }
        for (Book book : books) {
            if (book != null) {
                total += book.getPageCount();
            }
        }
        return total;
    }

    /**
     * Calculates the average number of pages across all books.
     *
     * @return average, or 0.0 if collection is null or empty
     */
    public static double calculateAveragePages(Collection<Book> books) {
        if (books == null || books.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        int count = 0;
        for (Book book : books) {
            if (book != null) {
                total += book.getPageCount();
                count++;
            }
        }
        if (count == 0) {
            return 0.0;
        }
        return (double) total / count;
    }

    /**
     * Finds the author whose books have been borrowed the most times in total.
     *
     * @return Optional containing the author name, or empty if no books or all null
     */
    public static Optional<String> findMostPopularAuthor(Collection<Book> books) {
        if (books == null || books.isEmpty()) {
            return Optional.empty();
        }
        Map<String, Integer> borrowCountByAuthor = new HashMap<>();
        for (Book book : books) {
            if (book == null) {
                continue;
            }
            String author = book.getAuthor();
            int newCount = borrowCountByAuthor.getOrDefault(author, 0) + book.getTimesBorrowed();
            borrowCountByAuthor.put(author, newCount);
        }
        if (borrowCountByAuthor.isEmpty()) {
            return Optional.empty();
        }
        String bestAuthor = null;
        int max = -1;
        for (Map.Entry<String, Integer> entry : borrowCountByAuthor.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                bestAuthor = entry.getKey();
            }
        }
        return Optional.ofNullable(bestAuthor);
    }

    /**
     * Calculates the percentage of books that are currently checked out.
     *
     * @return a value between 0.0 and 100.0
     */
    public static double calculateCheckedOutPercentage(Collection<Book> books) {
        if (books == null || books.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        int checkedOut = 0;
        for (Book book : books) {
            if (book == null) {
                continue;
            }
            total++;
            if (book.isCheckedOut()) {
                checkedOut++;
            }
        }
        if (total == 0) {
            return 0.0;
        }
        return (checkedOut * 100.0) / total;
    }

    /**
     * Calculates a "library engagement score" based on total times borrowed and
     * number of books.
     *
     * <p>Score is defined as: (sum of timesBorrowed) / max(1, number of books)</p>
     */
    public static double calculateEngagementScore(Collection<Book> books) {
        if (books == null || books.isEmpty()) {
            return 0.0;
        }
        int totalTimesBorrowed = 0;
        int count = 0;
        for (Book book : books) {
            if (book == null) {
                continue;
            }
            totalTimesBorrowed += book.getTimesBorrowed();
            count++;
        }
        if (count == 0) {
            return 0.0;
        }
        return (double) totalTimesBorrowed / count;
    }
}

