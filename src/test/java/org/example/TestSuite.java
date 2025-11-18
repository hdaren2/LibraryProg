package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestSuite {

    @Test
    public void testBookAuthor() {
        Book book1 = new Book("12345", "1984", "George Orwell", 1949, 340);
        String author1 = book1.getAuthor();
        assertEquals("George Orwell", author1);
    }

    @Test
    public void testBookID() {
        Book book1 = new Book("12345", "1984", "George Orwell", 1949, 340);
        String id1 = book1.getId();
        assertEquals("12345", id1);
    }

    @Test
    public void testBookTitle() {
        Book book1 = new Book("12345", "1984", "George Orwell", 1949, 340);
        String title1 = book1.getTitle();
        assertEquals("1984", title1);
    }

    @Test
    public void testBookPublishYear() {
        Book book1 = new Book("12345", "1984", "George Orwell", 1949, 340);
        int year1 = book1.getPublicationYear();
        assertEquals(1949, year1);
    }

    @Test
    public void testBookPageCount() {
        Book book1 = new Book("12345", "1984", "George Orwell", 1949, 340);
        int pageCount = book1.getPageCount();
        assertEquals(340, pageCount);
    }

    @Test
    void testBookNullID() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(null, "1984", "George Orwell", 1949, 340));
    }

    @Test
    void testBookNullTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("12345", null, "George Orwell", 1949, 340));
    }

    @Test
    void testBookNullAuthor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("12345", "1984", null, 1949, 340));
    }

    @Test
    void testBookNegPublicationYear() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("12345", "1984", "George Orwell", -1, 340));
    }

    @Test
    void testBookNegPageCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("12345", "1984", "George Orwell", 1949, -1));
    }

    @Test
    void libraryAddRemoveBook() {
        Library library = new Library();
        Book book = new Book("12345", "1984", "George Orwell", 1949, 340);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        library.addBook(book);
        Book idResult = library.findBookById("12345");
        List<Book> nameResult = library.findBooksByTitle("1984");
        assertEquals(book, idResult);
        assertEquals(bookList, nameResult);

        library.removeBook("12345");
        idResult = library.findBookById("12345");
        nameResult = library.findBooksByTitle("1984");
        assertEquals(null, idResult);
        assertEquals(new ArrayList<>(), nameResult);
    }
}