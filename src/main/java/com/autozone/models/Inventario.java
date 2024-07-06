package com.autozone.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventario {

	private List<Libro> inventario;

	public Inventario() {
		this.inventario = new ArrayList<Libro>();
		readBookData();
	}

	public void readBookData() {
		try (BufferedReader reader = new BufferedReader(
				new FileReader("C:\\Users\\jsilva3\\Documents\\Libreria\\books.csv"))) {

			String line;
			String[] bookData;
			reader.readLine();

			while ((line = reader.readLine()) != null) {
				bookData = line.split(",");

				inventario.add(new Libro(bookData[0], bookData[1], bookData[2],
						Float.parseFloat(bookData[3].replace("$", ""))));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agregarLibro(Libro libro) {
		inventario.add(libro);
		agregarLibroAlArchivo(libro);

	}

	public void agregarLibroAlArchivo(Libro libro) {
		
		if (searchByIsbn(libro.getIsbn()) == null) {
			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\jsilva3\\Documents\\Libreria\\books.csv", true))) {

				writer.write(libro.getCSVLine());
				writer.newLine();
				System.out.println("Libro " + libro.getTitulo() + " añadido.");

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El libro con ISBN: " + libro.getIsbn() + " ya se encuentra.");
		}

		
	}

	public void eliminarLibro(String isbn) {

		int i;
		boolean encontrado = false;

		for (i = 0; i < inventario.size(); i++) {
			if (inventario.get(i).getIsbn().equals(isbn)) {
				encontrado = true;
				break;
			}
		}

		if (encontrado) {
			inventario.remove(i);
			
			// Reescribir el archivo ahora sin el libro.
			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\jsilva3\\Documents\\Libreria\\books.csv"))) {

				for (Libro libro : inventario) {
					writer.write(libro.getCSVLine());
					writer.newLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Libro con isbn: " + isbn + " eliminado.");
		} else {
			System.out.println("Libro con ISBN: " + isbn + " no encontrado.");
		}
	

	}

	public void actualizarDetallesLibro(String isbn, String newTitle, String newAuthor, String newIsbn,
			float newPrice) {
		
		eliminarLibro(isbn);
		agregarLibro(new Libro(newTitle, newAuthor, newIsbn, newPrice));
	}
	
	public Libro searchByIsbn(String isbn) {
		for (Libro libro : inventario) {
			if (libro.getIsbn().equals(isbn)) {
				return libro;
			}
		}
		System.out.println("Libro con ISBN: " + isbn + " no encontrado.");
		return null;
	}

	public List<Libro> getInventario() {
		return inventario;
	}

	public void mostrarLibros() {
		for (Libro libro : inventario) {
			libro.mostrarDetalles();
		}
	}
}
