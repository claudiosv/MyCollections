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
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String firstName;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String lastName;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String companyName;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String address;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String telephoneNumber;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String emailAddress;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private ArrayList<String> parametreValueBuilder = new ArrayList<>();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private boolean exclusive;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private static final Logger logger = Logger.getLogger(Record.class.getName());

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public boolean isExclusive() {
        return exclusive;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ArrayList<String> getParametreValueBuilder() {
        return parametreValueBuilder;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setParametreValueBuilder(ArrayList<String> parametreValueBuilder) {
        this.parametreValueBuilder = parametreValueBuilder;
    }

    /**
     * Builds query to search SQLite database for records with matching parametres.
     * The caller sets the properties of a RecordSearchQuery and then
     * calls this method to create the SQL query needed to search
     * for the properties.
     *
     * @author Claudio Spiess
     * @return String of SQL to search database.
     */
    public String toLikeQuery() {
        logger.entering(getClass().getName(), "toLikeQuery");
        StringBuilder likeQueryBuilder = new StringBuilder();

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
                parametreValueBuilder.add(wildcardToSQL(entry.getValue()));
                counter++;
            }
        }
        logger.log(Level.INFO, "Query: ", likeQueryBuilder.toString());
        return likeQueryBuilder.toString();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private boolean emptyString(String searchString) {
        return searchString == null || searchString.trim().isEmpty();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String wildcardToSQL(String searchString) {
        return searchString.replace("*", "%");
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getAddress() {
        return address;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
