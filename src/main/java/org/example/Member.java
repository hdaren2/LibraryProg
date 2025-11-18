package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a person who is allowed to borrow books from the library.
 */
public class Member {

    private final String id;
    private String name;
    private int maxBooksAllowed;
    private final List<String> borrowedBookIds = new ArrayList<>();

    /**
     * Creates a new member.
     *
     * @param id              unique ID, must not be null or blank
     * @param name            member's name, must not be null or blank
     * @param maxBooksAllowed maximum number of books the member can borrow at once
     */
    public Member(String id, String name, int maxBooksAllowed) {
        if (isBlank(id)) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (isBlank(name)) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        if (maxBooksAllowed <= 0) {
            throw new IllegalArgumentException("maxBooksAllowed must be positive");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.maxBooksAllowed = maxBooksAllowed;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Updates the member's name.
     */
    public void setName(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
        this.name = name.trim();
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    /**
     * Updates the maximum number of books the member can borrow.
     * If the new limit is lower than the current borrowed count, this throws.
     */
    public void setMaxBooksAllowed(int maxBooksAllowed) {
        if (maxBooksAllowed <= 0) {
            throw new IllegalArgumentException("maxBooksAllowed must be positive");
        }
        if (maxBooksAllowed < borrowedBookIds.size()) {
            throw new IllegalArgumentException(
                    "New limit is lower than currently borrowed books");
        }
        this.maxBooksAllowed = maxBooksAllowed;
    }

    /**
     * Returns an unmodifiable view of the borrowed book IDs.
     */
    public List<String> getBorrowedBookIds() {
        return Collections.unmodifiableList(borrowedBookIds);
    }

    /**
     * Returns the number of books currently borrowed.
     */
    public int getBorrowedCount() {
        return borrowedBookIds.size();
    }

    /**
     * Returns true if the member has already borrowed the given book ID.
     */
    public boolean hasBorrowed(String bookId) {
        return borrowedBookIds.contains(bookId);
    }

    /**
     * Records that the member borrows a book.
     *
     * @param bookId book ID, must not be null or blank
     * @throws IllegalStateException    if the member already reached maximum capacity
     * @throws IllegalArgumentException if the book is already borrowed by this member
     */
    public void borrowBook(String bookId) {
        if (isBlank(bookId)) {
            throw new IllegalArgumentException("bookId must not be null or blank");
        }
        if (borrowedBookIds.size() >= maxBooksAllowed) {
            throw new IllegalStateException("Member has reached maxBooksAllowed");
        }
        if (borrowedBookIds.contains(bookId)) {
            throw new IllegalArgumentException("Member already borrowed this book: " + bookId);
        }
        borrowedBookIds.add(bookId);
    }

    /**
     * Records that the member returns a book.
     *
     * @param bookId book ID to return
     * @throws IllegalArgumentException if the book is not currently borrowed
     */
    public void returnBook(String bookId) {
        if (!borrowedBookIds.remove(bookId)) {
            throw new IllegalArgumentException("Member did not borrow book: " + bookId);
        }
    }

    /**
     * Returns true if the member currently has reached their borrowing capacity.
     */
    public boolean isAtCapacity() {
        return borrowedBookIds.size() >= maxBooksAllowed;
    }

    /**
     * Determines whether the member is a "power reader", defined as borrowing at least
     * a certain number of books at once.
     *
     * @param threshold number of books used as threshold
     */
    public boolean isPowerReader(int threshold) {
        if (threshold <= 0) {
            throw new IllegalArgumentException("threshold must be positive");
        }
        return borrowedBookIds.size() >= threshold;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", maxBooksAllowed=" + maxBooksAllowed +
                ", borrowedBookIds=" + borrowedBookIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        // Members are equal if they share the same id.
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
