package com.autozone.databases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.autozone.models.Libro;
import com.autozone.models.Transaction;

public class TransactionsResultSet {

	private ArrayList<Transaction> data;

	public TransactionsResultSet(ResultSet results) throws SQLException {
		
		this.data = new ArrayList<Transaction>();
		
		while (results.next()) {
			// TODO transaction( args )
			Transaction t = new Transaction(results.getString("titulo"), results.getString("isbn"), 
					results.getString("nombre"), results.getString("user_id"), results.getString("estatus"), 
					results.getString("fecha_prestamo"));
			
			data.add(t);
		}
	}
	
	public void printResults() throws SQLException {

		
		for (Transaction transaction : data) {
			transaction.mostrarTransaccion();
		}
		
		System.out.println("\n" + data.size() + " resultado(s) obtenidos.");
	}
}
