package main.it.unibz.MyCollections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by claudio on 30/03/2017.
 */
public class DatabaseHandler {
    public void initialise()
    {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS entries (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	userid integer NOT NULL,\n"
                    + " FOREIGN KEY(userid) REFERENCES users(id),\n"
                    + "	firstname TEXT,\n"
                    + "	lastname TEXT,\n"
                    + "	companyname TEXT,\n"
                    + "	address TEXT,\n"
                    + "	telephonenumber TEXT,\n"
                    + "	email TEXT,\n"
                    + "	picture BLOB,\n"
                    + ");";

            /*first name, last name, company name, address, telephone number, e-mail address,*/
            stmt.execute(sql);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }
}
