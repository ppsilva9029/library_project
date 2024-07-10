package com.autozone.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

public class LibraryConsoleUI {

    private static LibraryDAO libraryDAO = new LibraryDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Update Book Info");
            System.out.println("4. Search Book");
            System.out.println("5. Register New Member");
            System.out.println("6. Delete Member");
            System.out.println("7. Update Member Info");
            System.out.println("8. Search Member");
            System.out.println("9. Register Book Loan");
            System.out.println("10. Register Book Return");
            System.out.println("11. Show Loan History");
            System.out.println("12. Check Book Availability");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
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
                        registerNewMember();
                        break;
                    case 6:
                        deleteMember();
                        break;
                    case 7:
                        updateMemberInfo();
                        break;
                    case 8:
                        searchMember();
                        break;
                    case 9:
                        registerBookLoan();
                        break;
                    case 10:
                        registerBookReturn();
                        break;
                    case 11:
                        showLoanHistory();
                        break;
                    case 12:
                        checkBookAvailability();
                        break;
                    case 13:
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

    private static void checkBookAvailability() throws SQLException {
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        boolean isAvailable = libraryDAO.libroEstaDisponible(isbn);
        System.out.println("Book availability: " + (isAvailable ? "Available" : "Not Available"));
    }
}
