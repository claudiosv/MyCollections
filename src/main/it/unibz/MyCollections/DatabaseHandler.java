package main.it.unibz.MyCollections;

import main.it.unibz.MyCollections.exceptions.RecordAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.RecordNotFoundException;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;

import javax.imageio.ImageIO;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by claudio on 30/03/2017.
 */
public class DatabaseHandler {
    private static RecordsHandler instance = new SQLiteHandler();

    private DatabaseHandler() {
    }

    public static RecordsHandler getInstance() {
        return instance;
    }
}
