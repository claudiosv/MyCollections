package main.it.unibz.MyCollections;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        DatabaseHandler.getInstance().initialise();
        Login login = new Login();
        login.main(args);
        //create exceptions
        //implement logging
        //document everything
        //refactor entire project to fit design patterns
        //split into MVC
        //over do the whole inheritance thing e.g. DatabaseHandler interface,
        //then implement SQLiteHandler, etc.
        //aim for 100% test coverage
        //refactor databasehandler functions into Models to act as an ORM
        //extend any components needed
        // --> menus for export, import, save db, manage users, summary of data
        //create view to manage users
        //create view to edit user
        //create view to create user
        //create view for summary of data
        //make menu context aware
        //make record store id once inserted

    }
}
