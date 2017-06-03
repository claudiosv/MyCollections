package main.it.unibz.MyCollections;

public class Main {

    public static void main(String[] args) {
        DatabaseHandler.getInstance().initialise();
        Login login = new Login();
        login.launch(args);

        //TODO: Create exceptions needed for User and Record errors, etc.
        //TODO: Implement loggers
        //TODO: Add documentation
        //TODO: Refactor to fit design patterns
        //TODO: Split into MVC
        //TODO: Refactor things to work with inheritance e.g. SQLiteHandler
        //TODO: Write tests, aim for maximum coverage
        //TODO: Make main menu context aware
        //TODO: Add more options to context menus
        //TODO: Correct titles, add icons

        //TODO: Refactor password checking
        //TODO: Add button to end search mode
        //TODO: Rewrite any equality checks on strings etc to use .equals
        //TODO: Add check for usernames
        //TODO: Create 50 dummy records
        //TODO: Check users for permissions when working with stuff
        //TODO: Make sessions have an impact on records, etc. i.e. make sessions work


    }
}
