package com.autozone.app;

import java.sql.SQLException;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LibraryConsoleUI.loop();        
        
    }
}
