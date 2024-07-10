package com.autozone.models;

public class Transaction {

	private String bookTitle;
	private String isbn;
	private String nombreMiembro;
	private String miembroUserId;
	private String estatus;
	private String fechaPrestamo;
	
	public Transaction(String bookTitle, String isbn, String nombreMiembro, String miembroUserId, String estatus,
			String fechaPrestamo) {
		this.bookTitle = bookTitle;
		this.isbn = isbn;
		this.nombreMiembro = nombreMiembro;
		this.miembroUserId = miembroUserId;
		this.estatus = estatus;
		this.fechaPrestamo = fechaPrestamo;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getNombreMiembro() {
		return nombreMiembro;
	}

	public void setNombreMiembro(String nombreMiembro) {
		this.nombreMiembro = nombreMiembro;
	}

	public String getMiembroUserId() {
		return miembroUserId;
	}

	public void setMiembroUserId(String miembroUserId) {
		this.miembroUserId = miembroUserId;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	
	public void mostrarTransaccion() {
		
		// TODO metodo de mostrar transaction
		System.out.println("Préstamo: ["
				+ getBookTitle() + ", ISBN: " + getIsbn() + "" +"]");
	}
}
