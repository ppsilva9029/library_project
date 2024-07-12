package com.autozone.databases;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.autozone.models.Libro;
import com.autozone.models.Miembro;

public class LibraryDAOTest {

    public static LibraryDAO libraryDAO;

    @BeforeClass
    public static void setUp() {
        libraryDAO = new LibraryDAO();
        String isbn = "1234567890";
        try {
            libraryDAO.eliminarLibro(isbn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertarLibro() throws SQLException {
        Libro libro = new Libro("Test Title", "Test Author", "1234567890", 10);
        libraryDAO.insertarLibro(libro, 10);
        
        // Verificar que el titulo del libro coincida con el titulo insertado
        BooksResultSet brs = libraryDAO.buscarLibroIsbn("1234567890", false);
        assertEquals("Test Title", brs.getData().get(0).getTitulo());
    }

    @Test
    public void testEliminarLibro() throws SQLException {
        String isbn = "1234567890";
        libraryDAO.eliminarLibro(isbn);
        
        // Verificar que el libro fue eliminado
        BooksResultSet brs = libraryDAO.buscarLibroIsbn("1234567890", false);
        assertEquals(0, brs.getData().size());
    }

    @Test
    public void testActualizarInfoLibro() throws SQLException {
        Libro libro = new Libro("Old Title", "Old Author", "1234567890", 10);
        libraryDAO.insertarLibro(libro, 10);
        
        libraryDAO.actualizarInfoLibro("1234567890", "New Title", "New Author", "0987654321", 15);
        
        BooksResultSet brs = libraryDAO.buscarLibroIsbn("0987654321", false);
        assertEquals("New Title", brs.getData().get(0).getTitulo());
        assertEquals("New Author", brs.getData().get(0).getAutor());
        assertEquals(15, brs.getData().get(0).getQuantity());
    }

    @Test
    public void testActualizarCantidadLibro() throws SQLException {
        Libro libro = new Libro("Test Title", "Test Author", "1234567890", 10);
        libraryDAO.insertarLibro(libro, 10);
        
        libraryDAO.actualizarCantidadLibro("1234567890", 20);
        
        BooksResultSet brs = libraryDAO.buscarLibroIsbn("1234567890", false);
        assertEquals(20, brs.getData().get(0).getQuantity());
    }

    @Test
    public void testInsertarMiembro() throws SQLException {
        Miembro miembro = new Miembro("Test User", "user123");
        libraryDAO.insertarMiembro(miembro);
        
        MembersResultSet mrs = libraryDAO.buscarMiembroUserId("user123", false);
        assertEquals("Test User", mrs.getData().get(0).getNombre());
    }

    @Test
    public void testEliminarMiembro() throws SQLException {
        Miembro miembro = new Miembro("Test User", "user123");
        libraryDAO.insertarMiembro(miembro);
        
        libraryDAO.eliminarMiembro("user123");
        
        MembersResultSet mrs = libraryDAO.buscarMiembroUserId("user123", false);
        assertEquals(0, mrs.getData().size());
    }

    @AfterClass
    public static void tearDownAfterClass() throws SQLException {
        String isbn = "1234567890";
        libraryDAO.eliminarLibro(isbn);
        
        libraryDAO.eliminarMiembro("user123");
    }
}
