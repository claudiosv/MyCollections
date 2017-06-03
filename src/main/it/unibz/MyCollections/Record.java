package main.it.unibz.MyCollections;

import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by claudio on 22/03/2017.
 */
public class Record {
    private int recordId;
    private int ownerUserId;
    //private String firstName;
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty companyName = new SimpleStringProperty("");
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty telephoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty emailAddress = new SimpleStringProperty("");
    private BufferedImage bufImage;
    private Image image;
    private ImageView imageView;

    public Record() {
        try {
            //InputStream st = this.getClass().getResourceAsStream();
            this.image = new Image("default_user.png", 48, 48, true, true);
            this.setBufImage(SwingFXUtils.fromFXImage(image, null));//ImageIO.read(st));
            this.imageView = getImageView();
        } catch (IOException ex) {
            ex.printStackTrace(); //TODO: logger
        }
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

    public void save() {
    }



    public void delete() {
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

    //public void setImage(Image image) {
    //    this.image = image;
    //}


    public BufferedImage getBufImage() {
        return bufImage;
    }

    public void setBufImage(BufferedImage bufImage) throws IOException {
        this.bufImage = bufImage;
        //SwingFXUtils.fromFXImage
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (bufImage == null) return;
        ImageIO.write(bufImage, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        this.image = new Image(is, 48, 48, true, true);
        this.imageView = getImageView();
    }

    public byte[] getRecordImageArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bufImage == null) return null;
        ImageIO.write(bufImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public ImageView getImageView() throws IOException {
        return new ImageView(image);
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
        return this.getFirstName();
    } //TODO: write more!

    //public void setImageView(ImageView imageView) {
    //    this.imageView = imageView;
    //}
}
