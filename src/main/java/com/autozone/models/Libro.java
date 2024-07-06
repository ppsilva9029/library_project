package com.autozone.models;

public class Libro {
	
	private String titulo;
	private String autor;
	private String isbn;
	private float precio;
	
	public Libro(String titulo, String autor, String isbn, float precio) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.precio = precio;
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

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public void mostrarDetalles() {
		System.out.println("[" + getTitulo() + " - " + getAutor() + ", ISBN: " + getIsbn() + ", Precio: $" + getPrecio() + "]");
	}
	
	public String getCSVLine() {
		return getTitulo() + "," + getAutor() + "," + getIsbn() + "," + getPrecio();
	}
	
	
	
	
	
}
