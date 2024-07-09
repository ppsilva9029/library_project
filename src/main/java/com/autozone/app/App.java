package com.autozone.app;

import java.sql.SQLException;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LibraryDAO dao = new LibraryDAO();
        
        Libro libro = new Libro("El arte de la guerra", "Sun Tzu", "95959595954", 2);
        try {
        	
        	
			dao.buscarLibroIsbn("", true);
		} catch (IllegalArgumentException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} 
        
        
    }
}
