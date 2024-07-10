package com.autozone.models;

public class Libro {
	
	private String titulo;
	private String autor;
	private String isbn;
	private int quantity;
	
	public Libro(String titulo, String autor, String isbn, int quantity) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.quantity = quantity;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/*
	 * public boolean isAvailable() {
	 * 
	 * }
	 */


	public void mostrarDetalles() {
		System.out.println("[" + getTitulo() + " - " + getAutor() + ", ISBN: " + getIsbn() + "]");
	}
	
	public void mostrarDisponibilidad() {
		System.out.println("Libro: [" + getTitulo() + " - " + getAutor() + ", ISBN: " + getIsbn() + "]"
								+ " Cantidad disponible: " + quantity);
	}

	
	
	
	
}
