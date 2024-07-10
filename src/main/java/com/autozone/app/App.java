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
        	
        	System.out.println(dao.transaccionExiste("978014143918", "juanM@gmail.com"));
        	
		} catch (IllegalArgumentException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} 
        
        
    }
}
