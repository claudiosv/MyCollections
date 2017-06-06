package main.it.unibz.MyCollections;

/**
 * Factory to instantiate DatabaseHandlers.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class DatabaseHandlerFactory {

    /**
     * Instantiates a DatabaseHandler depending on the type
     * specified by the caller.
     *
     * @param handlerType Which type of handler e.g. sqlite
     * @author Claudio Spiess
     * @return DatabaseHandler instance of the specified handlerType
     * @see DatabaseHandler
     * @see SQLiteHandler
     */
    public DatabaseHandler getDatabaseHandler(String handlerType) {
        if (handlerType == null) {
            return null;
        }

        if (handlerType.equalsIgnoreCase("sqlite")) {
            return new SQLiteHandler();
        }

        return null;
    }
}
