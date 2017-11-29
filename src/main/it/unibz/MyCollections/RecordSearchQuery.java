package main.it.unibz.MyCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Record search query builder. This class represents
 * a unique search of the database. It contains a map
 * of all the parametres the user is searching for
 * and the values they are looking for. It builds the right
 * SQL query to search for the right properties.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class RecordSearchQuery {
    /**
     * Holds the logger for this class.
     */
    private static final Logger logger = Logger.getLogger(Record.class.getName());

    /**
     * Holds the firstName to search for.
     */
    private String firstName;

    /**
     * Holds the lasfName to search for.
     */
    private String lastName;

    /**
     * Holds the companyName to search for.
     */
    private String companyName;

    /**
     * Holds the address to search for.
     */
    private String address;

    /**
     * Holds the telephoneNumber to search for.
     */
    private String telephoneNumber;

    /**
     * Holds the emailAddress to search for.
     */
    private String emailAddress;

    /**
     * Holds the SQL parameters to search for. i.e. the list of parameters to pass to the
     * PreparedStatement.
     */
    private ArrayList<String> parameterValueBuilder;

    /**
     * Holds the whether the search should be exclusive or not.
     */
    private boolean exclusive;

    /**
     * Sets whether the search is exclusive.
     *
     * @param exclusive Whether to search exclusively or not.
     */
    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    /**
     * Gets the parameter value builder.
     *
     * @return Parameters to pass to PreparedStatement
     */
    public ArrayList<String> getParameterValueBuilder() {
        return parameterValueBuilder;
    }

    /**
     * Builds query to search SQLite database for records with matching parametres.
     * The caller sets the properties of a RecordSearchQuery and then
     * calls this method to create the SQL query needed to search
     * for the properties.
     *
     * @return String of SQL to search database.
     * @author Claudio Spiess
     */
    public String toLikeQuery() {
        logger.entering(getClass().getName(), "toLikeQuery");
        StringBuilder likeQueryBuilder = new StringBuilder();
        parameterValueBuilder = new ArrayList<>();
        HashMap<String, String> schemaMap = new HashMap<>();
        schemaMap.put("firstname", firstName);
        schemaMap.put("lastname", lastName);
        schemaMap.put("companyname", companyName);
        schemaMap.put("address", address);
        schemaMap.put("telephonenumber", telephoneNumber);
        schemaMap.put("email", emailAddress);

        int counter = 0;
        for (Map.Entry<String, String> entry : schemaMap.entrySet()) {
            if (!emptyString(entry.getValue())) {
                if (counter > 0) likeQueryBuilder.append(exclusive ? " AND " : " OR ");
                likeQueryBuilder.append(entry.getKey());
                likeQueryBuilder.append(" LIKE ?");
                parameterValueBuilder.add(wildcardToSQL(entry.getValue()));
                counter++;
            }
        }
        logger.log(Level.INFO, "Query: ", likeQueryBuilder.toString());
        return likeQueryBuilder.toString();
    }

    /**
     * Gets whether a string is considered empty/whitespace
     *
     * @param searchString String to check for emptiness.
     * @return <code>true</code> if the searchString is empty.
     */
    private boolean emptyString(String searchString) {
        return searchString == null || searchString.trim().isEmpty();
    }

    /**
     * Replaces wildcards with SQL wildcards.
     *
     * @param searchString String to replace wildcards with SQL wildcards.
     * @return String with replaced wildcards.
     */
    private String wildcardToSQL(String searchString) {
        return searchString.replace("*", "%");
    }

    /**
     * Sets the firstName.
     *
     * @param firstName The new firstName value.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastName.
     *
     * @param lastName The new lastName value.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the companyName.
     *
     * @param companyName The new companyName value.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Sets the address.
     *
     * @param address The new address value.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the telephone number.
     *
     * @param telephoneNumber The new telephoneNumber value.
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Sets the emailAddress.
     *
     * @param emailAddress The new emailAddress value.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
