package com.autozone.databases;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.autozone.models.Libro;

public class LibraryDAOTest {

    private LibraryDAO libraryDAO;

    @BeforeEach
    void setUp() {
        libraryDAO = new LibraryDAO();
        String isbn = "1234567890";
        try {
			libraryDAO.eliminarLibro(isbn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    void testInsertarLibro() throws SQLException {
        Libro libro = new Libro("Test Title", "Test Author", "1234567890", 10);
        libraryDAO.insertarLibro(libro, 10);
        // Add assertions to verify the book was inserted correctly
    }

    @Test
    void testEliminarLibro() throws SQLException {
        String isbn = "1234567890";
        libraryDAO.eliminarLibro(isbn);
        // Add assertions to verify the book was deleted correctly
    }

    // Add more test methods for other operations
}

