package main.it.unibz.MyCollections;

import java.io.IOException;
import java.util.logging.*;
/** Main class to implement the task.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Main {
    private static final Logger logger = Logger.getLogger("main.it.unibz.MyCollections");

    public static void main(String[] args) {
        logger.setLevel(Level.ALL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINER);
        logger.addHandler(consoleHandler);

        try {
            FileHandler handler = new FileHandler("MyCollections-log.%u.%g.txt", 1024 * 1024 * 8, 10, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex)
        {
            logger.log(Level.SEVERE, "Log file failed to open", ex);
        }

        logger.log(Level.INFO, "Logging started, opening database");
        DatabaseSession.getInstance().initialise("test.db");
        Login login = new Login();
        login.main(args);

        //TODO: Implement loggers
        //TODO: Add documentation
        //TODO: Refactor to fit design patterns
        //TODO: Split into MVC
        //TODO: Refactor things to work with inheritance e.g. SQLiteHandler
        //TODO: Write tests, aim for maximum coverage
        //TODO: Add more options to context menus
        //TODO: Correct titles, add icons
        //TODO: Refactor password checking
        //TODO: Add button to end search mode
        //TODO: Rewrite any equality checks on strings etc to use .equals
        //TODO: Add check for username, passwords
        //TODO: Check users for permissions when working with stuff
        //TODO: Make sessions have an impact on records, etc. i.e. make sessions work
        //TODO: fix user image too
    }
}
