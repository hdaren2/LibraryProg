package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a small in-memory library system.
 *
 * <p>This class purposely has multiple responsibilities and logic branches
 * to give you good material for tests: error handling, searching, sorting,
 * checking invariants, etc.</p>
 */
public class Library {

    private final Map<String, Book> booksById = new HashMap<>();
    private final Map<String, Member> membersById = new HashMap<>();

    /**
     * Adds a book to the library.
     *
     * @throws IllegalArgumentException if id is already used or book is null
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }
        if (booksById.containsKey(book.getId())) {
            throw new IllegalArgumentException("Book with id " + book.getId() + " already exists");
        }
        booksById.put(book.getId(), book);
    }

    /**
     * Removes a book from the library.
     *
     * @param bookId ID of the book to remove
     * @return true if removed, false if no such book existed
     * @throws IllegalStateException if the book is currently checked out
     */
    public boolean removeBook(String bookId) {
        Book book = booksById.get(bookId);
        if (book == null) {
            return false;
        }
        if (book.isCheckedOut()) {
            throw new IllegalStateException("Cannot remove a book that is checked out");
        }
        booksById.remove(bookId);
        return true;
    }

    /**
     * Registers a new member in the library.
     */
    public void registerMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("member must not be null");
        }
        if (membersById.containsKey(member.getId())) {
            throw new IllegalArgumentException("Member with id " + member.getId() + " already exists");
        }
        membersById.put(member.getId(), member);
    }

    /**
     * Unregisters a member. Member must have no borrowed books.
     *
     * @return true if the member existed and was removed
     * @throws IllegalStateException if the member still has borrowed books
     */
    public boolean unregisterMember(String memberId) {
        Member member = membersById.get(memberId);
        if (member == null) {
            return false;
        }
        if (!member.getBorrowedBookIds().isEmpty()) {
            throw new IllegalStateException("Cannot unregister a member with borrowed books");
        }
        membersById.remove(memberId);
        return true;
    }

    public Book findBookById(String id) {
        return booksById.get(id);
    }

    public Member findMemberById(String id) {
        return membersById.get(id);
    }

    /**
     * Returns all books as an unmodifiable collection.
     */
    public Collection<Book> getAllBooks() {
        return Collections.unmodifiableCollection(booksById.values());
    }

    /**
     * Returns all members as an unmodifiable collection.
     */
    public Collection<Member> getAllMembers() {
        return Collections.unmodifiableCollection(membersById.values());
    }

    /**
     * Finds all books whose title contains the given query (case-insensitive).
     */
    public List<Book> findBooksByTitle(String query) {
        if (query == null) {
            throw new IllegalArgumentException("query must not be null");
        }
        String lower = query.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : booksById.values()) {
            if (book.getTitle().toLowerCase().contains(lower)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books by a specific author (case-insensitive match).
     */
    public List<Book> findBooksByAuthor(String author) {
        if (author == null) {
            throw new IllegalArgumentException("author must not be null");
        }
        String lower = author.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : booksById.values()) {
            if (book.getAuthor().toLowerCase().equals(lower)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns a list of all currently available (not checked-out) books.
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : booksById.values()) {
            if (!book.isCheckedOut()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns a list of all currently checked-out books.
     */
    public List<Book> getCheckedOutBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : booksById.values()) {
            if (book.isCheckedOut()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Checks out a book for a member.
     *
     * @throws IllegalArgumentException if the book or member does not exist
     * @throws IllegalStateException    if the book is already checked out or member at capacity
     */
    public void checkoutBook(String bookId, String memberId) {
        Book book = booksById.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No such book: " + bookId);
        }
        Member member = membersById.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("No such member: " + memberId);
        }
        if (book.isCheckedOut()) {
            throw new IllegalStateException("Book is already checked out: " + bookId);
        }
        if (member.isAtCapacity()) {
            throw new IllegalStateException("Member is at capacity: " + memberId);
        }
        // If we reach here, operation is allowed.
        book.checkOut();
        member.borrowBook(bookId);
    }

    /**
     * Returns a book from a member.
     *
     * @throws IllegalArgumentException if the book or member does not exist,
     *                                  or if the member didn't actually borrow the book
     */
    public void returnBook(String bookId, String memberId) {
        Book book = booksById.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No such book: " + bookId);
        }
        Member member = membersById.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("No such member: " + memberId);
        }
        if (!member.hasBorrowed(bookId)) {
            throw new IllegalArgumentException("Member did not borrow book: " + bookId);
        }
        book.checkIn();
        member.returnBook(bookId);
    }

    /**
     * Returns all books borrowed by a member, as a list.
     */
    public List<Book> getBooksBorrowedByMember(String memberId) {
        Member member = membersById.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("No such member: " + memberId);
        }
        List<Book> result = new ArrayList<>();
        for (String bookId : member.getBorrowedBookIds()) {
            Book book = booksById.get(bookId);
            if (book != null) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns the most popular books, sorted by timesBorrowed descending.
     *
     * @param limit maximum number of books to return; if <= 0, returns empty list
     */
    public List<Book> getMostPopularBooks(int limit) {
        if (limit <= 0) {
            return Collections.emptyList();
        }
        List<Book> all = new ArrayList<>(booksById.values());
        all.sort(Comparator.comparingInt(Book::getTimesBorrowed).reversed());
        if (limit >= all.size()) {
            return all;
        }
        return new ArrayList<>(all.subList(0, limit));
    }

    /**
     * Returns the total number of books currently in the library.
     */
    public int getTotalBookCount() {
        return booksById.size();
    }

    /**
     * Returns the total number of registered members.
     */
    public int getTotalMemberCount() {
        return membersById.size();
    }

    /**
     * Calculates the total number of books currently checked out across all members.
     */
    public int getTotalBorrowedBookCount() {
        int total = 0;
        for (Member member : membersById.values()) {
            total += member.getBorrowedCount();
        }
        return total;
    }

    /**
     * Returns true if the library has at least one book, one member,
     * and at least one book is currently checked out.
     */
    public boolean isActive() {
        return !booksById.isEmpty()
                && !membersById.isEmpty()
                && getTotalBorrowedBookCount() > 0;
    }
}
