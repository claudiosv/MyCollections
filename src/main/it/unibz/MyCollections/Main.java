package main.it.unibz.MyCollections;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        //System.out.println(System.getProperty("java.class.path"));
        DatabaseHandler.getInstance().initialise();
        Login login = new Login();
        login.main(args);

        //Create exceptions needed for User and Record errors, etc.
        //Implement loggers
        //Add documentation
        //Add property for admin users
        //Refactor to fit design patterns
        //Split into MVC
        //Refactor things to work with inheritance e.g. SQLiteHandler
        //Write tests, aim for maximum coverage
        //Make main menu context aware
        //Add more options to context menu
        //Make record store id once inserted
        //Correct titles, add icons


    }
}
