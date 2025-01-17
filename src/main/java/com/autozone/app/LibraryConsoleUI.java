package com.autozone.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

public class LibraryConsoleUI {

	private static LibraryDAO libraryDAO = new LibraryDAO();
	private static Scanner scanner = new Scanner(System.in);
	private static String bigLine = "----------------------------------------------------------------------------\n";

	public static void loop() {
		while (true) {
			System.out.println("Library Management System\n");
			System.out.println(" Seleccione el tipo de operación que desea realizar.");
			System.out.println("  1. Libros (Añadir, eliminar, buscar)");
			System.out.println("  2. Miembros");
			System.out.println("  3. Préstamos de libros (Registro de préstamo, devolución, historial) ");
			System.out.println("  4. Exit");
			System.out.print(" Número de la categoría: ");
			
			try {
				
				int categoryChoice = scanner.nextInt();
				scanner.nextLine(); // Consume newline

				System.out.println(bigLine);
				
				switch (categoryChoice) {
				case 1:
					bookOperations();
					break;
				case 2:
					memberOperations();
					break;
				case 3:
					loanOperations();
					break;
				case 4:
					System.out.println("Saliendo...");
					return;
				default:
					System.out.println("Opción inválida, debe ingresar un número entero.");
				}
			} catch (SQLException | IllegalArgumentException e) {
				System.out.println("Ocurrió un error: " + e.getMessage());
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println("Se debe ingresar un número entero válido.");
				
			} catch (Exception e) {
				System.out.println("Ocurrió un error: " + e.getMessage());
				System.out.println("Saliendo...");
				return;
			} finally {
				
				System.out.println(bigLine);
			}

			
		}
	}

	private static void bookOperations() throws SQLException {
		System.out.println("Libros");
		System.out.println(" 1. Añadir libro");
		System.out.println(" 2. Eliminar libro");
		System.out.println(" 3. Actualizar información del libro");
		System.out.println(" 4. Buscar (nombre, autor, ISBN)");
		System.out.println(" 5. Consultar disponibilidad.\n");
		System.out.print("Elija una opción: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		switch (choice) {
		case 1:
			addBook();
			break;
		case 2:
			deleteBook();
			break;
		case 3:
			updateBookInfo();
			break;
		case 4:
			searchBook();
			break;
		case 5:
			checkBookAvailability();
			break;
		default:
			System.out.println("Opción inválida, debe ingresar un número entero.");
		}
	}

	private static void memberOperations() throws SQLException {
		System.out.println("Miembros.");
		System.out.println(" 1. Registrar nuevo Miembro");
		System.out.println(" 2. Eliminar Miembro");
		System.out.println(" 3. Actualizar información (nombre, email)");
		System.out.println(" 4. Buscar (por nombre, email)");
		System.out.print("Opción elegida: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		switch (choice) {
		case 1:
			registerNewMember();
			break;
		case 2:
			deleteMember();
			break;
		case 3:
			updateMemberInfo();
			break;
		case 4:
			searchMember();
			break;
		default:
			System.out.println("Opción inválida, debe ingresar un número entero.");
		}
	}

	private static void loanOperations() throws SQLException {
		System.out.println("Préstamos de libros.");
		System.out.println(" 1. Registrar préstamo");
		System.out.println(" 2. Registrar devolución");
		System.out.println(" 3. Historial de préstamos de un libro");
		System.out.print("Opción elegida: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		switch (choice) {
		case 1:
			registerBookLoan();
			break;
		case 2:
			registerBookReturn();
			break;
		case 3:
			showLoanHistory();
			break;
		default:
			System.out.println("Opción inválida, por favor intente de nuevo.");
		}
	}

	// Book Operations
	private static void addBook() throws SQLException {
		System.out.print("Título del libro: ");
		String title = scanner.nextLine();
		System.out.print("Autor: ");
		String author = scanner.nextLine();
		System.out.print("ISBN: ");
		String isbn = scanner.nextLine();
		System.out.print("Cantidad: ");
		int quantity = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Libro libro = new Libro(title, author, isbn, quantity);
		libraryDAO.insertarLibro(libro, quantity);
	}

	private static void deleteBook() throws SQLException {
		System.out.print("ISBN del libro a eliminar: ");
		String isbn = scanner.nextLine();
		libraryDAO.eliminarLibro(isbn);
	}

	private static void updateBookInfo() throws SQLException {
		System.out.print("ISBN actual del libro: ");
		String isbn = scanner.nextLine();
		System.out.print("Nuevo título: ");
		String newTitle = scanner.nextLine();
		System.out.print("Autor: ");
		String newAuthor = scanner.nextLine();
		System.out.print("ISBN nuevo: ");
		String newIsbn = scanner.nextLine();
		System.out.print("Cantidad disponible: ");
		int newQuantity = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		libraryDAO.actualizarInfoLibro(isbn, newTitle, newAuthor, newIsbn, newQuantity);
	}

	private static void searchBook() throws SQLException {
		System.out.println("Buscar por: \n 1. Título\n 2. Autor\n 3. ISBN");
		System.out.print("Opción elegida: ");
		int searchChoice = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		System.out.print("Inserte término de búsqueda: ");
		String searchTerm = scanner.nextLine();
		boolean printResults = true;

		switch (searchChoice) {
		case 1:
			libraryDAO.buscarLibroTitulo(searchTerm, printResults);
			break;
		case 2:
			libraryDAO.buscarLibroAutor(searchTerm, printResults);
			break;
		case 3:
			libraryDAO.buscarLibroIsbn(searchTerm, printResults);
			break;
		default:
			System.out.println("Opción de búsqueda inválida.");
		}
	}

	private static void checkBookAvailability() throws SQLException {
		System.out.print("ISBN: ");
		String isbn = scanner.nextLine();

		boolean isAvailable = libraryDAO.libroEstaDisponible(isbn);
		System.out.println("Estado del libro: " + (isAvailable ? "Disponible" : "No disponible"));
	}

	// Member Operations
	private static void registerNewMember() throws SQLException {
		System.out.println("Datos del nuevo miembro.");
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		System.out.print("email: ");
		String userId = scanner.nextLine();
		
		if (!validateEmail(userId)) {
			System.out.println("El email introducido no es válido.");
			return;
		}	

		Miembro miembro = new Miembro(name, userId);
		libraryDAO.insertarMiembro(miembro);
	}

	private static void deleteMember() throws SQLException {
		System.out.print("Email del miembro a eliminar: ");
		String userId = scanner.nextLine();
		
		if (!validateEmail(userId)) {
			System.out.println("El email introducido no es válido.");
			return;
		}
		
		libraryDAO.eliminarMiembro(userId);
	}

	private static void updateMemberInfo() throws SQLException {
		System.out.print("Email actual del miembro: ");
		String userId = scanner.nextLine();
		
		if (!validateEmail(userId)) {
			System.out.println("El email actual introducido no es válido.");
			return;
		}
		System.out.print("Nuevo nombre: ");
		String newName = scanner.nextLine();
		System.out.print("Nuevo Email: ");
		String newUserId = scanner.nextLine();
		
		if (!validateEmail(userId)) {
			System.out.println("El nuevo email introducido no es válido.");
			return;
		}
		libraryDAO.actualizarInfoMiembro(userId, newName, newUserId);
	}

	private static void searchMember() throws SQLException {
		System.out.println("Buscar por:\n 1. Nombre\n 2. Email");
		System.out.print("Criterio: ");
		int searchChoice = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		System.out.print("Término de búsqueda: ");
		String searchTerm = scanner.nextLine();
		boolean printResults = true;

		switch (searchChoice) {
		case 1:
			libraryDAO.buscarMiembroNombre(searchTerm, printResults);
			break;
		case 2:
			libraryDAO.buscarMiembroUserId(searchTerm, printResults);
			break;
		default:
			System.out.println("Opción de búsqueda inválida.");
		}
	}

	// Loan Operations
	private static void registerBookLoan() throws SQLException {
		System.out.print("ISBN del libro a prestar: ");
		String isbn = scanner.nextLine();
		System.out.print("Email del miembro que lo pide prestado: ");
		String userId = scanner.nextLine();
		
		if (!validateEmail(userId)) {
			System.out.println("El email introducido no es válido.");
			return;
		}
		System.out.print("Fecha del préstamo (YYYY-MM-DD): ");
		String fecha = scanner.nextLine();

		libraryDAO.registrarPrestamo(isbn, userId, fecha);
	}

	private static void registerBookReturn() throws SQLException {
		System.out.print("ISBN del libro a devolver: ");
		String isbn = scanner.nextLine();
		System.out.print("Email del miembro que lo devuelve: ");
		String userId = scanner.nextLine();
		
		if (!validateEmail(userId)) {
			System.out.println("El email introducido no es válido.");
			return;
		}
		
		System.out.print("Fecha de devolución (YYYY-MM-DD): ");
		String fecha = scanner.nextLine();

		libraryDAO.registrarDevolucion(isbn, userId, fecha);
	}

	private static void showLoanHistory() throws SQLException {
		System.out.println("Mostrar historial de préstamos.");
		System.out.print("ISBN del libro: ");
		String isbn = scanner.nextLine();
		boolean printResults = true;

		libraryDAO.historialPrestamosLibro(isbn, printResults);
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}

}
