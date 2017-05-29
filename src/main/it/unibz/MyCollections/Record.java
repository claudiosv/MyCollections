package main.it.unibz.MyCollections;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by claudio on 22/03/2017.
 */
public class Record {
    private int recordId;
    private int ownerUserId;
    //private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String telephoneNumber;
    private String emailAddress;
    private Image image;
    private SimpleStringProperty firstName;

    public Record(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.image = new Image("/1496072626_user.png")
    }

    public Record() {

    }

    public void save(){};
    public void delete(){};

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        ImageView img = new ImageView(getImage());
        return img;
    }
}
