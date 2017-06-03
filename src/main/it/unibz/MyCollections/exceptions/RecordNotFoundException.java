package main.it.unibz.MyCollections.exceptions;

/**
 * Created by claudio on 03/06/2017.
 */
public class RecordNotFoundException extends Exception {
    public RecordNotFoundException()
    {
        super("Record not found");
    }
}
