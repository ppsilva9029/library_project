package com.autozone.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.autozone.databases.LibraryDAO;
import com.autozone.models.Libro;
import com.autozone.models.Miembro;

/**
 * Hello world!
 *
 */
public class App 
{
	
	//private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args )
    
    {
    	
        LibraryConsoleUI.loop();        
        
    }
}
