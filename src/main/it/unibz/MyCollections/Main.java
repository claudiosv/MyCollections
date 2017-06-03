package main.it.unibz.MyCollections;

public class Main {

    public static void main(String[] args) {
        DatabaseHandler.getInstance().initialise();
        Login login = new Login();
        login.main(args);

        //Create exceptions needed for User and Record errors, etc.
        //Implement loggers
        //Add documentation
        //Refactor to fit design patterns
        //Split into MVC
        //Refactor things to work with inheritance e.g. SQLiteHandler
        //Write tests, aim for maximum coverage
        //Make main menu context aware
        //Add more options to context menus
        //Correct titles, add icons

        //Refactor password checking
        //Add button to end search mode
        //Rewrite any equality checks on strings etc to use .equals
        //Add check for usernames
        //Create 50 dummy records
        //Check users for permissions when working with stuff
        //Make sessions have an impact on records, etc. i.e. make sessions work


    }
}
