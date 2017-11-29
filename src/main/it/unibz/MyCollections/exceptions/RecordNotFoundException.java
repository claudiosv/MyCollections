package main.it.unibz.MyCollections.exceptions;

/**
 * Exception for when a method attempts to update or delete a Record
 * from the database, but the record does not exist. Specifically,
 * this exception is thrown when no matching row id is found for the
 * Record's id.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see main.it.unibz.MyCollections.Record
 * @see Exception
 * @since 1.0
 */
public class RecordNotFoundException extends Exception {

    /**
     * Instantiates the exception for a Record that could not be found
     * by calling the super constructor in the Exception class
     *
     * @author Claudio Spiess
     * @see main.it.unibz.MyCollections.Record
     */
    public RecordNotFoundException() {
        super("Record not found");
    }
}
