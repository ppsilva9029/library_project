package com.autozone.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.autozone.databases.DatabaseConnection;
import com.autozone.models.Libro;

/**
* Returns the Data Access Object to the library database. 
*  
* <p>
* 
* @see         DatabaseConnection
*/
public class LibraryDAO {
	
	
	/**
	* Inserts the book to the library database.
	* <p>
	* If the book is already in the database, only the quantity available would increase.
	*
	* @param  libro  the book to be added
	* @param  quantity integer > 1
	*/
	public void insertBook(Libro libro, int quantity) throws IllegalArgumentException, SQLException {
		
		if (quantity < 1) {
			throw new IllegalArgumentException("La cantidad no puede ser menor a 1.");
		}
		
		String sql = "INSERT INTO tbl_libros (titulo, autor, isbn, quantity_available)\n"
				+ "VALUES (?, ?, ?, ?) as new_ \n"
				+ "ON DUPLICATE KEY UPDATE tbl_libros.quantity_available = tbl_libros.quantity_available + new_.quantity_available;";
		
		try(Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			
			
			statement.setString(1, libro.getTitulo());
			statement.setString(2, libro.getAutor());
			statement.setString(3, libro.getIsbn());
			statement.setInt(4, quantity);
			
			statement.execute();
			
			System.out.println("Libro \"" + libro.getTitulo() + "\" añadido (" + quantity + ").");
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	public

}
