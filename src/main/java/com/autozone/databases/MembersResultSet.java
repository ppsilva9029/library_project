package com.autozone.databases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.autozone.models.Miembro;

public class MembersResultSet {

	private ArrayList<Miembro> data;

	public MembersResultSet(ResultSet results) throws SQLException {
		
		this.data = new ArrayList<Miembro>();
		
		while (results.next()) {
			Miembro miembro = new Miembro(results.getString("nombre"), results.getString("user_id"));
			data.add(miembro);
			
		}
	}
	
	public void printResults() throws SQLException {

		
		for (Miembro miembro : data) {
			miembro.mostrarDatos();
		}
		
		System.out.println("\n" + data.size() + " resultado(s) obtenidos.");
	}
}
