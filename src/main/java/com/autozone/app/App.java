package com.autozone.app;

import java.sql.SQLException;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LibraryDAO dao = new LibraryDAO();
        
        Miembro miembro = new Miembro("Hector Gomez", "hgomez@outlook.com");
        try {
        	
        	dao.registrarDevolucion("9780141439518", "juanM@gmail.com", "2024-02-05");
        	
		} catch (IllegalArgumentException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} 
        
        
    }
}
