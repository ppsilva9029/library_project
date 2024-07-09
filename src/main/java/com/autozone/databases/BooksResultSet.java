package com.autozone.databases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.autozone.models.Libro;

public class BooksResultSet {
	
	//private ResultSet results;
	private ArrayList<Libro> data;

	public BooksResultSet(ResultSet results) throws SQLException {
		
		this.data = new ArrayList<Libro>();
		
		while (results.next()) {
			Libro libro = new Libro(results.getString("titulo"), results.getString("autor"), results.getString("isbn"), results.getInt("quantity_available"));
			data.add(libro);
			
		}
	}
	
	public void printResults() throws SQLException {

		
		for (Libro libro : data) {
			libro.mostrarDisponibilidad();
		}
		
		System.out.println("\n" + data.size() + " resultado(s) obtenidos.");
	}

}
