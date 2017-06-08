package main.it.unibz.MyCollections;

import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a record. A record is an item
 * in the collection. Each record has a unique
 * id which represents the row id in the database.
 * A record contains the unique user id of the owner
 * of the Record, and contains the properties:
 * first name, last name, company name,
 * address, telephone number, and email address.
 * It also stores the image of the record.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Record {

    /**
     * Holds the logger for this class.
     */
    private static final Logger logger = Logger.getLogger(Record.class.getName());

    /**
     * Holds the firstName of this record.
     */
    private final SimpleStringProperty firstName = new SimpleStringProperty("");

    /**
     * Holds the lastName of this record.
     */
    private final SimpleStringProperty lastName = new SimpleStringProperty("");

    /**
     * Holds the companyName of this record.
     */
    private final SimpleStringProperty companyName = new SimpleStringProperty("");

    /**
     * Holds the address of this record.
     */
    private final SimpleStringProperty address = new SimpleStringProperty("");

    /**
     * Holds the telephone number of this record.
     */
    private final SimpleStringProperty telephoneNumber = new SimpleStringProperty("");

    /**
     * Holds the email address of this record.
     */
    private final SimpleStringProperty emailAddress = new SimpleStringProperty("");

    /**
     * Holds the database id of this record.
     */
    private int recordId;

    /**
     * Holds the id of the user who owns/created this record.
     */
    private int ownerUserId;

    /**
     * Holds the JavaFX image of this record.
     */
    private Image image;

    /**
     * Holds the JavaFX {@link ImageView} of this record.
     */
    private ImageView imageView;

    /**
     * Sets the default image of the record and creates a JavaFX image view.
     */
    public void setDefaultImage() {
        try {
            this.image = new Image("default_user.png", 48, 48, true, true);
            imageView = new ImageView(image);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    /**
     * Gets the first name. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return Record's first name property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gets the last name. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return Record's last name property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Gets the company name. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return Record's company name property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    /**
     * Gets the address. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return Record's address property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty addressProperty() {
        return address;
    }

    /**
     * Gets the telephone number. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return Record's telephone number property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty telephoneNumberProperty() {
        return telephoneNumber;
    }

    /**
     * Gets the email address. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return Record's email address property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty emailAddressProperty() {
        return emailAddress;
    }

    /**
     * Gets the record Id. This is the id of the row in the database.
     *
     * @return <code>int</code> id of the record
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * Sets the record Id.
     *
     * @param recordId The new record Id value to set.
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    /**
     * Gets the owner user Id. This is the id of the user who owns/created the record in the database.
     *
     * @return <code>int</code> id of the owner user Id.
     */
    public int getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * Sets the owner user Id.
     *
     * @param ownerUserId The new owner user Id value to set.
     */
    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * Gets the first name string.
     *
     * @return Record's first name
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Sets the first name.
     *
     * @param firstName The new first name value to set.
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gets the last name string.
     *
     * @return Record's last name
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Sets the last name.
     *
     * @param lastName The new last name value to set.
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Gets the company name string.
     *
     * @return Record's company name
     */
    public String getCompanyName() {
        return companyName.get();
    }

    /**
     * Sets the company name.
     *
     * @param companyName The new company name value to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    /**
     * Gets the address string.
     *
     * @return Record's address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Sets the address.
     *
     * @param address The new address value to set.
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Gets the telephone number.
     *
     * @return Record's telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber.get();
    }

    /**
     * Sets the telephone number.
     *
     * @param telephoneNumber The new telephone number value to set.
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(telephoneNumber);
    }

    /**
     * Gets the email address.
     *
     * @return Record's email address
     */
    public String getEmailAddress() {
        return emailAddress.get();
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress The new email address value to set.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    /**
     * Gets the Record's image.
     *
     * @return Record's image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the Record's image.
     *
     * @param image The new image to set.
     */
    public void setImage(Image image) {
        this.image = image;
        imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
    }

    /**
     * Gets the image view.
     *
     * @return An {@link ImageView} of the Record
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Writes Record's image into a byte array for IO operations.
     *
     * @return Bytes of the Record's image.
     * @author Claudio Spiess
     */
    public byte[] getImageArray() {
        logger.entering(getClass().getName(), "getImageArray");
        try {
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedImage bufImage = SwingFXUtils.fromFXImage(image, null);
                if (bufImage == null) return null;
                ImageIO.write(bufImage, "jpg", baos);
                return baos.toByteArray();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error writing image array", ex);
        }
        return new byte[0];
    }

    /**
     * Gets whether a Record is considered empty.
     * A record is considered empty when all of its text fields are blank.
     *
     * @return Returns <code>true</code> if all text fields are blank, otherwise <code>false</code>.
     */
    public boolean isEmpty() {
        if (firstName != null && !firstName.getValue().trim().equals("")) return false;
        if (lastName != null && !lastName.getValue().trim().equals("")) return false;
        if (companyName != null && !companyName.getValue().trim().equals("")) return false;
        if (address != null && !address.getValue().trim().equals("")) return false;
        if (telephoneNumber != null && !telephoneNumber.getValue().trim().equals("")) return false;
        return !(emailAddress != null && !emailAddress.getValue().trim().equals(""));
    }

    /**
     * Compares this object against another object.
     *
     * @param o Object to compare
     * @return Returns whether the object O is considered equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (recordId != record.recordId) return false;
        if (ownerUserId != record.ownerUserId) return false;
        if (firstName != null ? !firstName.getValue().equals(record.firstName.getValue()) : record.firstName.getValue() != null)
            return false;
        if (lastName != null ? !lastName.getValue().equals(record.lastName.getValue()) : record.lastName.getValue() != null)
            return false;
        if (companyName != null ? !companyName.getValue().equals(record.companyName.getValue()) : record.companyName.getValue() != null)
            return false;
        if (address != null ? !address.getValue().equals(record.address.getValue()) : record.address.getValue() != null)
            return false;
        if (telephoneNumber != null ? !telephoneNumber.getValue().equals(record.telephoneNumber.getValue()) : record.telephoneNumber.getValue() != null)
            return false;
        if (emailAddress != null ? !emailAddress.getValue().equals(record.emailAddress.getValue()) : record.emailAddress.getValue() != null)
            return false;
        return image != null ? image.equals(record.image) : record.image == null;
    }

    /**
     * Converts parametres of Record into a string.
     *
     * @return String of the Record's parametres.
     * @author Claudio Spiess
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s", this.getFirstName(), this.getLastName(), this.getCompanyName(), this.getAddress(), this.getTelephoneNumber(), this.getEmailAddress());
    }
}
