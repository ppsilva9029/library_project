package com.autozone.app;

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
        
        Libro libro = new Libro("El arte de la guerra", "Sun Tzu", "95959595954");
        try {
			dao.insertBook(libro, 3);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
}
