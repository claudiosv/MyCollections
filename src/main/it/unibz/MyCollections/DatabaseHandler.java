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
            String sql = "CREATE TABLE IF NOT EXISTS records (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	name text NOT NULL,\n"
                    + "	capacity real\n"
                    + ");";
            stmt.execute(sql);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }
}
