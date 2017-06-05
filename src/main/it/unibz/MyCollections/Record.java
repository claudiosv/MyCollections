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

/** Represents a record.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Record {
    private int recordId;
    private int ownerUserId;
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty companyName = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty telephoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty emailAddress = new SimpleStringProperty("");
    private Image image;
    private ImageView imageView;
    private static final Logger logger = Logger.getLogger(Record.class.getName());

    public Record() {
            this.image = new Image("default_user.png", 48, 48, true, true);
            imageView = new ImageView(image);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty telephoneNumberProperty() {
        return telephoneNumber;
    }

    public SimpleStringProperty emailAddressProperty() {
        return emailAddress;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getTelephoneNumber() {
        return telephoneNumber.get();
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(telephoneNumber);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
    }

    public byte[] getImageArray() {
        logger.entering(getClass().getName(), "getImageArray");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bufImage = SwingFXUtils.fromFXImage(image, null);
            if (bufImage == null) return null;
            ImageIO.write(bufImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException ex)
        {
            logger.log(Level.SEVERE, "Error writing image array", ex);
        }
        finally {
            return new byte[0];
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isEmpty() {
        if (firstName != null && !firstName.getValue().trim().equals("")) return false;
        if (lastName != null && !lastName.getValue().trim().equals("")) return false;
        if (companyName != null && !companyName.getValue().trim().equals("")) return false;
        if (address != null && !address.getValue().trim().equals("")) return false;
        if (telephoneNumber != null && !telephoneNumber.getValue().trim().equals("")) return false;
        if (emailAddress != null && !emailAddress.getValue().trim().equals("")) return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s", this.getFirstName(), this.getLastName(), this.getCompanyName(), this.getAddress(), this.getTelephoneNumber(), this.getEmailAddress());
    }
}
