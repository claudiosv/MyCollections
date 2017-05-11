package main.it.unibz.MyCollections;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        new DatabaseHandler().initialise();
        Login login = new Login();
        login.main(args);
    }
}
