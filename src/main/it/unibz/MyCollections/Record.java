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
    private int recordId;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private int ownerUserId;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private final SimpleStringProperty firstName = new SimpleStringProperty("");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private final SimpleStringProperty lastName = new SimpleStringProperty("");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private final SimpleStringProperty companyName = new SimpleStringProperty("");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private final SimpleStringProperty address = new SimpleStringProperty("");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private final SimpleStringProperty telephoneNumber = new SimpleStringProperty("");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private final SimpleStringProperty emailAddress = new SimpleStringProperty("");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private Image image;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private ImageView imageView;

    /**
     * Instantiates Record object. Sets the default image of the record
     * and creates a JavaFX image view.
     *
     * @author Claudio Spiess
     */
    public Record() {

    }

    public void setDefaultImage() {
        this.image = new Image("default_user.png", 48, 48, true, true);
        imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty addressProperty() {
        return address;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty telephoneNumberProperty() {
        return telephoneNumber;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty emailAddressProperty() {
        return emailAddress;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public int getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getCompanyName() {
        return companyName.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getTelephoneNumber() {
        return telephoneNumber.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(telephoneNumber);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getEmailAddress() {
        return emailAddress.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public Image getImage() {
        return image;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setImage(Image image) {
        this.image = image;
        imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
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
            if(image != null) {
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
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public boolean isEmpty() {
        if (firstName != null && !firstName.getValue().trim().equals("")) return false;
        if (lastName != null && !lastName.getValue().trim().equals("")) return false;
        if (companyName != null && !companyName.getValue().trim().equals("")) return false;
        if (address != null && !address.getValue().trim().equals("")) return false;
        if (telephoneNumber != null && !telephoneNumber.getValue().trim().equals("")) return false;
        return !(emailAddress != null && !emailAddress.getValue().trim().equals(""));
    }

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
