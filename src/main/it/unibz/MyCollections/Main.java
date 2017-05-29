package main.it.unibz.MyCollections;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        DatabaseHandler.getInstance().initialise();
        Login login = new Login();
        login.main(args);
        //refactor database to soft delete records
        //create exceptions
        //implement logging
        //document everything
        //refactor entire project to fit design patterns
        //split into MVC
        //over do the whole inheritance thing e.g. DatabaseHandler interface,
        //then implement SQLiteHandler, etc.
        //aim for 100% test coverage
        //refactor databasehandler functions into Models to act as an ORM
        //create Login validator
        //extend any components needed
        //create view for login
        //create view for main list
        // --> create right click menu
        // --> menus for export, import, save db, manage users, summary of data
        //create view for update record
        //create view for insert record
        //create view to manage users
        //create view to edit user
        //create view to create user
        //create view for summary of data
    }
}
