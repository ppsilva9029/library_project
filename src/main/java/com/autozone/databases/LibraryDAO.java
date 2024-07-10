package com.autozone.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.autozone.databases.DatabaseConnection;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

/**
 * Returns the Data Access Object to the library database.
 * 
 * <p>
 * 
 * @see DatabaseConnection
 */
public class LibraryDAO {

	/**
	 * Inserts the book to the library database.
	 * <p>
	 * If the book is already in the database, only the quantity available would
	 * increase.
	 *
	 * @param libro    the book to be added
	 * @param quantity integer > 1
	 */
	public void insertarLibro(Libro libro, int quantity) throws IllegalArgumentException, SQLException {

		if (quantity < 1) {
			throw new IllegalArgumentException("La cantidad no puede ser menor a 1.");
		}

		String sql = "INSERT INTO tbl_libros (titulo, autor, isbn, quantity_available)\n"
				+ "VALUES (?, ?, ?, ?) as new_ \n"
				+ "ON DUPLICATE KEY UPDATE tbl_libros.quantity_available = tbl_libros.quantity_available + new_.quantity_available;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, libro.getTitulo());
			statement.setString(2, libro.getAutor());
			statement.setString(3, libro.getIsbn());
			statement.setInt(4, quantity);

			statement.execute();

			System.out.println("Libro \"" + libro.getTitulo() + "\" añadido (" + quantity + ").");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private BooksResultSet buscarLibro(String argumento, String criterio, boolean printResults) throws SQLException {

		if (argumento.length() < 1) {
			throw new IllegalArgumentException("El campo de búsqueda no debe estar vacío.");
		}

		String sql = "SELECT titulo, autor, isbn, quantity_available from tbl_libros " + "where " + criterio
				+ " like ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, "%" + argumento + "%");

			BooksResultSet brs = new BooksResultSet(statement.executeQuery());

			if (printResults) {
				brs.printResults();
			}
			return brs;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * Search a book by its ISBN, and prints the results if needed.
	 */

	public BooksResultSet buscarLibroIsbn(String isbn, boolean printResults) throws SQLException {
		return buscarLibro(isbn, "isbn", printResults);
	}

	/**
	 * Search a book by its Title, and prints the results if needed.
	 */
	public BooksResultSet buscarLibroTitulo(String titulo, boolean printResults) throws SQLException {
		return buscarLibro(titulo, "titulo", printResults);
	}

	/**
	 * Search a book by its Author, and prints the results if needed.
	 */
	public BooksResultSet buscarLibroAutor(String autor, boolean printResults) throws SQLException {
		return buscarLibro(autor, "autor", printResults);
	}

	/**
	 * Update the book info based on its ISBN.
	 * 
	 * The newQuantity param must be > 0
	 */
	public void actualizarInfoLibro(String isbn, String newTitle, String newAuthor, String newIsbn, int newQuantity)
			throws SQLException {

		if (!existeLibro(isbn)) {
			System.out.println("Este libro no existe.");
			return;
		}

		if (newQuantity < 0) {
			System.out.println("La cantidad no debe ser negativa.");
			return;
		}

		String sql = "UPDATE tbl_libros set titulo = ?, autor = ?, isbn = ?, quantity_available = ? "
				+ "where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newTitle);
			statement.setString(2, newAuthor);
			statement.setString(3, newIsbn);
			statement.setInt(4, newQuantity);
			statement.setString(5, isbn);

			statement.execute();
			System.out.println("Datos del libro actualizados.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void actualizarCantidadLibro(String isbn, int newQuantity) throws SQLException {

		if (!existeLibro(isbn)) {
			System.out.println("Este libro no existe.");
			return;
		}

		if (newQuantity < 0) {
			System.out.println("La cantidad no debe ser negativa.");
			return;
		}

		String sql = "UPDATE tbl_libros set quantity_available = ? " + "where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, newQuantity);
			statement.setString(2, isbn);

			statement.execute();
			System.out.println("Datos del libro actualizados.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private void restarLibro(String isbn) throws SQLException {

		String sql = "UPDATE tbl_libros set quantity_available = quantity_available - 1 " + "where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);

			statement.execute();
			System.out.println("Cantidad disponible disminuida.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private void sumarLibro(String isbn) throws SQLException {

		String sql = "UPDATE tbl_libros set quantity_available = quantity_available + 1 " + "where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);

			statement.execute();
			System.out.println("Cantidad disponible aumentada.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void eliminarLibro(String isbn) throws SQLException {

		// TODO eliminar transacciones de ese libro si e=fue prestado.
		String sql = "DELETE from tbl_libros where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}

	}

	public boolean existeMiembro(String userId) throws SQLException {
		String sql = "SELECT count(*) from tbl_miembros where user_id = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			rs.next();

			return rs.getInt(1) > 0;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public boolean existeLibro(String isbn) throws SQLException {
		String sql = "SELECT count(*) from tbl_libros where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);
			ResultSet rs = statement.executeQuery();
			rs.next();

			return rs.getInt(1) > 0;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void insertarMiembro(Miembro miembro) throws SQLException {
		if (existeMiembro(miembro.getUserId())) {
			System.out.println("Este miembro ya existe.");
			return;
		}

		String sql = "INSERT INTO tbl_miembros(nombre, user_id) VALUES(?, ?);";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, miembro.getNombre());
			statement.setString(2, miembro.getUserId());

			statement.execute();

			System.out.println("Usuario añadido.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void eliminarMiembro(String userId) throws SQLException {

		if (!existeMiembro(userId)) {
			System.out.println("Este usuario no existe.");
			return;
		}

		// TODO eliminar transacciones de ese miembro si obtuvo un prestamo.
		String sql = "DELETE from tbl_miembros where user_id = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, userId);
			statement.execute();

			System.out.println("Ususario eliminado.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void actualizarInfoMiembro(String userId, String newName, String newUserId) throws SQLException {

		if (!existeMiembro(userId)) {
			System.out.println("Este miembro no existe.");
			return;
		}

		String sql = "UPDATE tbl_miembros set nombre = ?, user_id = ? " + "where user_id = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newName);
			statement.setString(2, newUserId);
			statement.setString(3, userId);

			statement.execute();

			System.out.println("Datos del miembro actualizados.");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private MembersResultSet buscarMiembro(String argumento, String criterio, boolean printResults)
			throws SQLException {

		if (argumento.length() < 1) {
			throw new IllegalArgumentException("El campo de búsqueda no debe estar vacío.");
		}

		String sql = "SELECT nombre, user_id from tbl_miembros " + "where " + criterio + " like ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, "%" + argumento + "%");

			MembersResultSet mrs = new MembersResultSet(statement.executeQuery());

			if (printResults) {
				mrs.printResults();
			}
			return mrs;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public MembersResultSet buscarMiembroNombre(String nombre, boolean printResults) throws SQLException {
		return buscarMiembro(nombre, "nombre", printResults);

	}

	public MembersResultSet buscarMiembroUserId(String userId, boolean printResults) throws SQLException {
		return buscarMiembro(userId, "user_id", printResults);

	}

	public boolean puedePedirLibro(String userId) throws SQLException {

		if (!existeMiembro(userId)) {
			return false;
		}

		String sql = "select count(*) from tbl_transacciones \n"
				+ "where miembro_id = (select id from tbl_miembros where user_id = ?) \n"
				+ "and estatus = 'no devuelto';";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			rs.next();

			int i = rs.getInt(1);
			return i < 1;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public boolean libroEstaDisponible(String isbn) throws SQLException {
		if (!existeLibro(isbn)) {
			return false;
		}

		String sql = "select quantity_available from tbl_libros \n" + "where isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);
			ResultSet rs = statement.executeQuery();
			rs.next();

			int i = rs.getInt(1);
			return i > 0;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}

	}

	public void registrarPrestamo(String isbn, String userId, String fecha) throws SQLException {

		if (!puedePedirLibro(userId)) {
			System.out.println("Este miembro no ha devuelto el último libro que pidió.");
			return;
		}

		if (!libroEstaDisponible(isbn)) {
			System.out.println("Este libro no está disponible.");
			return;
		}

		String sql = "insert into tbl_transacciones(libro_id, miembro_id, fecha_prestamo, estatus) \n"
				+ "values ((select id from tbl_libros where isbn = ?), \n"
				+ "(select id from tbl_miembros where user_id = ?), date(?), 'no devuelto'); ";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);
			statement.setString(2, userId);
			statement.setString(3, fecha);
			// statement.setString(4, isbn);

			statement.execute();

			restarLibro(isbn);
			System.out.println("Prestamo finalizado.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}

	}

	private boolean transaccionExiste(String isbn, String userId) throws SQLException {

		String sql = "select count(*) from tbl_transacciones \n"
				+ "where miembro_id = (select id from tbl_miembros where user_id = ?) \n"
				+ "and libro_id = (select id from tbl_libros where isbn = ?)" + "and estatus = 'no devuelto';";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, userId);
			statement.setString(2, isbn);

			ResultSet rs = statement.executeQuery();
			rs.next();

			int i = rs.getInt(1);
			return i > 0;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}

	}

	public void registrarDevolucion(String isbn, String userId, String fecha) throws SQLException {

		if (!transaccionExiste(isbn, userId)) {
			System.out.println("Este prestamo no esta registrado.");
			return;
		}

		String sql = "UPDATE tbl_transacciones set estatus = 'devuelto', fecha_devolucion = ? "
				+ "where miembro_id = (select id from tbl_miembros where user_id = ?) "
				+ "and libro_id = (select id from tbl_libros where isbn = ?)" + "and estatus = 'no devuelto';";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, fecha);
			statement.setString(2, userId);
			statement.setString(3, isbn);

			// statement.setString(4, isbn);

			statement.execute();

			sumarLibro(isbn);
			System.out.println("Libro devuelto.");

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}

	}

	public TransactionsResultSet historialPrestamosLibro(String isbn, boolean printResults) throws SQLException {

		String sql = "SELECT l.titulo, l.isbn, m.nombre, m.user_id, \n"
				+ "    t.estatus, DATE_FORMAT(t.fecha_prestamo, '%Y-%m-%d') as fecha_prestamo \n"
				+ "    FROM tbl_transacciones t JOIN \n"
				+ "    tbl_libros l ON t.libro_id = l.Id JOIN \n"
				+ "    tbl_miembros m ON t.miembro_id = m.Id WHERE l.isbn = ?;";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, isbn);

			TransactionsResultSet trs = new TransactionsResultSet(statement.executeQuery());

			if (printResults) {
				trs.printResults();
			}
			return trs;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

}
