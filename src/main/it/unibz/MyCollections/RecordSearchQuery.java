package main.it.unibz.MyCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 17/05/2017.
 */
public class RecordSearchQuery {
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String telephoneNumber;
    private String emailAddress;
    private ArrayList<String> parametreValueBuilder = new ArrayList<>();
    private boolean exclusive;
    private static final Logger logger = Logger.getLogger(Record.class.getName());

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public ArrayList<String> getParametreValueBuilder() {
        return parametreValueBuilder;
    }

    public void setParametreValueBuilder(ArrayList<String> parametreValueBuilder) {
        this.parametreValueBuilder = parametreValueBuilder;
    }

    public String toLikeQuery(boolean exclusive) {
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

    public boolean emptyString(String searchString) {
        return searchString == null || searchString.trim().isEmpty();
    }

    public String wildcardToSQL(String searchString) {
        return searchString.replace("*", "%");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
