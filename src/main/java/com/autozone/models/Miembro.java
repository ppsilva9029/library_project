package com.autozone.models;

public class Miembro {

	private String nombre;
	private String userId;
	
	
	
	public Miembro(String nombre, String userId) {
		this.nombre = nombre;
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void mostrarDatos() {
		System.out.println("Miembro: [Nombre: "+ getNombre() + ", userId: "+ getUserId() +"]");
	}
	
	
}
