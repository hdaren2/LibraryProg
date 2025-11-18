package org.example;

import java.util.Objects;

/**
 * Represents a book in the library.
 * <p>
 * This class is intentionally a bit "busy" so that you have a lot of
 * behavior to write tests for: validation, state changes, exceptions, etc.
 */
public class Book {

    private final String id;
    private String title;
    private String author;
    private int publicationYear;
    private int pageCount;
    private boolean checkedOut;
    private int timesBorrowed;

    /**
     * Creates a new book.
     *
     * @param id              unique identifier, must not be null or blank
     * @param title           title of the book, must not be null or blank
     * @param author          author of the book, must not be null or blank
     * @param publicationYear year the book was published (must be > 0)
     * @param pageCount       number of pages (must be > 0)
     */
    public Book(String id,
                String title,
                String author,
                int publicationYear,
                int pageCount) {
        if (isBlank(id)) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (isBlank(title)) {
            throw new IllegalArgumentException("title must not be null or blank");
        }
        if (isBlank(author)) {
            throw new IllegalArgumentException("author must not be null or blank");
        }
        if (publicationYear <= 0) {
            throw new IllegalArgumentException("publicationYear must be positive");
        }
        if (pageCount <= 0) {
            throw new IllegalArgumentException("pageCount must be positive");
        }
        this.id = id.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.checkedOut = false;
        this.timesBorrowed = 0;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title new title, must not be null or blank
     */
    public void setTitle(String title) {
        if (isBlank(title)) {
            throw new IllegalArgumentException("title must not be null or blank");
        }
        this.title = title.trim();
    }

    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author new author, must not be null or blank
     */
    public void setAuthor(String author) {
        if (isBlank(author)) {
            throw new IllegalArgumentException("author must not be null or blank");
        }
        this.author = author.trim();
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Updates the publication year.
     * <p>
     * For demonstration purposes, we disallow changing the year to a value
     * earlier than 1400, to mimic a validation rule.
     *
     * @param publicationYear new year, must be >= 1400
     */
    public void setPublicationYear(int publicationYear) {
        if (publicationYear < 1400) {
            throw new IllegalArgumentException("publicationYear must be >= 1400");
        }
        this.publicationYear = publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    /**
     * Updates the page count.
     *
     * @param pageCount new page count, must be > 0
     */
    public void setPageCount(int pageCount) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("pageCount must be positive");
        }
        this.pageCount = pageCount;
    }

    /**
     * Returns whether the book is currently checked out.
     */
    public boolean isCheckedOut() {
        return checkedOut;
    }

    /**
     * Marks the book as checked out.
     * <p>
     * If the book is already checked out, this method throws an
     * {@link IllegalStateException}.
     */
    public void checkOut() {
        if (checkedOut) {
            throw new IllegalStateException("Book is already checked out: " + id);
        }
        checkedOut = true;
        timesBorrowed++;
    }

    /**
     * Marks the book as returned.
     * <p>
     * If the book is not checked out, this method throws an
     * {@link IllegalStateException}.
     */
    public void checkIn() {
        if (!checkedOut) {
            throw new IllegalStateException("Book is not currently checked out: " + id);
        }
        checkedOut = false;
    }

    /**
     * Returns the number of times this book has been borrowed.
     */
    public int getTimesBorrowed() {
        return timesBorrowed;
    }

    /**
     * Returns true if this book is a "classic" according to a simple rule:
     * older than 50 years.
     *
     * @param currentYear current year; must be >= publicationYear
     */
    public boolean isClassic(int currentYear) {
        if (currentYear < publicationYear) {
            throw new IllegalArgumentException("currentYear cannot be before publicationYear");
        }
        return (currentYear - publicationYear) >= 50;
    }

    /**
     * Calculates a simple "reading difficulty score" based on page count and age.
     * This is completely arbitrary, just to give you something numeric to test.
     *
     * @param currentYear current year for age calculation
     * @return a score where higher means more "difficult"
     */
    public double calculateDifficultyScore(int currentYear) {
        int age = Math.max(0, currentYear - publicationYear);
        return pageCount * 0.5 + age * 0.1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        // Books are equal if they share the same id.
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", pageCount=" + pageCount +
                ", checkedOut=" + checkedOut +
                ", timesBorrowed=" + timesBorrowed +
                '}';
    }
}
