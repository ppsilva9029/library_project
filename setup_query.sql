drop database if exists DB_LIBRERIA;

create database DB_LIBRERIA;
USE DB_LIBRERIA;

drop table if exists tbl_libros;
CREATE TABLE tbl_libros (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    isbn VARCHAR(20) unique,
	quantity_available integer
);

drop user if exists 'librarian'@'localhost';
CREATE USER 'librarian'@'localhost' IDENTIFIED BY 'system';

GRANT ALL PRIVILEGES ON db_libreria.* TO 'librarian'@'localhost';


INSERT INTO tbl_libros (titulo, autor, isbn, quantity_available)
VALUES
    ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 10),
    ('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 15),
    ('1984', 'George Orwell', '9780451524935', 20),
    ('Pride and Prejudice', 'Jane Austen', '9780141439518', 5),
    ('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 12),
    ('Brave New World', 'Aldous Huxley', '9780060850524', 8),
    ('The Hobbit', 'J.R.R. Tolkien', '9780345339683', 18),
    ('Lord of the Flies', 'William Golding', '9780399501487', 7),
    ('The Alchemist', 'Paulo Coelho', '9780061122415', 25),
    ('The Shining', 'Stephen King', '9780307743657', 14),
    ('The Da Vinci Code', 'Dan Brown', '9780307474278', 9),
    ('The Hunger Games', 'Suzanne Collins', '9780439023481', 22);

drop table if exists tbl_miembros;
create table tbl_miembros(
	Id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    user_id VARCHAR(255) UNIQUE
);

insert into tbl_miembros(nombre, user_id) values ("Juan Perez", "juan@gmail.com"),
("Juan Gomez", "jgomez@outlook.com"),
("Juan M", "juanM@gmail.com"), 
("Hector Silva", "hsilva@gmail.com"),
("Lalo Perez", "lperez@outlook.com");

drop table if exists tbl_transacciones;
CREATE TABLE tbl_transacciones (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    libro_id INT NOT NULL,
    miembro_id INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    estatus ENUM('devuelto', 'no devuelto') NOT NULL,
    fecha_devolucion DATE,
    FOREIGN KEY (libro_id) REFERENCES tbl_libros(Id),
    FOREIGN KEY (miembro_id) REFERENCES tbl_miembros(Id)
);


insert into tbl_transacciones(libro_id, miembro_id, fecha_prestamo, estatus) 
values (1, 2, date("2024-01-01"), "no devuelto");

insert into tbl_transacciones(libro_id, miembro_id, fecha_prestamo, estatus) 
values (3, 4, date("2024-01-01"), "no devuelto");
