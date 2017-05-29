package main.it.unibz.MyCollections;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by claudio on 22/03/2017.
 */
public class Record {
    private int recordId;
    private int ownerUserId;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String telephoneNumber;
    private String emailAddress;
    private BufferedImage bufImage;
    private Image image;
    private ImageView imageView;
    //private SimpleStringProperty firstName;

    public Record(String firstName) {
        this.firstName = firstName;//new SimpleStringProperty(firstName);
        //Image img = new Image(new ByteArrayInputStream(getRecordImageArray()), 48, 48, true, true);
        this.image = new Image("default_user.png", 48, 48, true, true);
        try {
            this.imageView = getImageView();
            Image test = imageView.getImage();
            System.out.print("flce");
        }catch (Exception ex){}

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

    public Image getImage() {
        return image;
    }

    //public void setImage(Image image) {
    //    this.image = image;
    //}


    public BufferedImage getBufImage() {
        return bufImage;
    }

    public byte[] getRecordImageArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public void setBufImage(BufferedImage bufImage) {
        this.bufImage = bufImage;
    }

    public ImageView getImageView() throws IOException {
            ImageView imgv = new ImageView(image);
            return imgv;
    }

    //public void setImageView(ImageView imageView) {
    //    this.imageView = imageView;
    //}
}
