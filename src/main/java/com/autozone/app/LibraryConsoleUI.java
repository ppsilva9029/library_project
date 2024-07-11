package com.autozone.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

public class LibraryConsoleUI {

    private static LibraryDAO libraryDAO = new LibraryDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void loop() {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Book Operations");
            System.out.println("2. Member Operations");
            System.out.println("3. Loan Operations");
            System.out.println("4. Exit");
            System.out.print("Choose a category: ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
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
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void bookOperations() throws SQLException {
        System.out.println("Book Operations");
        System.out.println("1. Add Book");
        System.out.println("2. Delete Book");
        System.out.println("3. Update Book Info");
        System.out.println("4. Search Book");
        System.out.println("5. Check Book Availability");
        System.out.print("Choose an option: ");
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
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void memberOperations() throws SQLException {
        System.out.println("Member Operations");
        System.out.println("1. Register New Member");
        System.out.println("2. Delete Member");
        System.out.println("3. Update Member Info");
        System.out.println("4. Search Member");
        System.out.print("Choose an option: ");
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
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void loanOperations() throws SQLException {
        System.out.println("Loan Operations");
        System.out.println("1. Register Book Loan");
        System.out.println("2. Register Book Return");
        System.out.println("3. Show Loan History");
        System.out.print("Choose an option: ");
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
                System.out.println("Invalid option. Please try again.");
        }
    }

    // Book Operations
    private static void addBook() throws SQLException {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Libro libro = new Libro(title, author, isbn, quantity);
        libraryDAO.insertarLibro(libro, quantity);
    }

    private static void deleteBook() throws SQLException {
        System.out.print("Enter book ISBN to delete: ");
        String isbn = scanner.nextLine();
        libraryDAO.eliminarLibro(isbn);
    }

    private static void updateBookInfo() throws SQLException {
        System.out.print("Enter current book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter new book title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new book author: ");
        String newAuthor = scanner.nextLine();
        System.out.print("Enter new book ISBN: ");
        String newIsbn = scanner.nextLine();
        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        libraryDAO.actualizarInfoLibro(isbn, newTitle, newAuthor, newIsbn, newQuantity);
    }

    private static void searchBook() throws SQLException {
        System.out.println("Search by: 1. Title 2. Author 3. ISBN");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter search term: ");
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
                System.out.println("Invalid search criteria.");
        }
    }

    private static void checkBookAvailability() throws SQLException {
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        boolean isAvailable = libraryDAO.libroEstaDisponible(isbn);
        System.out.println("Book availability: " + (isAvailable ? "Available" : "Not Available"));
    }

    // Member Operations
    private static void registerNewMember() throws SQLException {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member user ID: ");
        String userId = scanner.nextLine();

        Miembro miembro = new Miembro(name, userId);
        libraryDAO.insertarMiembro(miembro);
    }

    private static void deleteMember() throws SQLException {
        System.out.print("Enter member user ID to delete: ");
        String userId = scanner.nextLine();
        libraryDAO.eliminarMiembro(userId);
    }

    private static void updateMemberInfo() throws SQLException {
        System.out.print("Enter current member user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter new member name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new member user ID: ");
        String newUserId = scanner.nextLine();

        libraryDAO.actualizarInfoMiembro(userId, newName, newUserId);
    }

    private static void searchMember() throws SQLException {
        System.out.println("Search by: 1. Name 2. User ID");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter search term: ");
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
                System.out.println("Invalid search criteria.");
        }
    }

    // Loan Operations
    private static void registerBookLoan() throws SQLException {
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter member user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter loan date (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        libraryDAO.registrarPrestamo(isbn, userId, fecha);
    }

    private static void registerBookReturn() throws SQLException {
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter member user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter return date (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        libraryDAO.registrarDevolucion(isbn, userId, fecha);
    }

    private static void showLoanHistory() throws SQLException {
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        boolean printResults = true;

        libraryDAO.historialPrestamosLibro(isbn, printResults);
    }
}
