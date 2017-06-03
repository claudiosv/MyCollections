package main.it.unibz.MyCollections.exceptions;

/**
 * Created by claudio on 03/06/2017.
 */
public class RecordAlreadyExistsException extends Exception {
    public RecordAlreadyExistsException()
    {
        super("Record already exists");
    }
}
