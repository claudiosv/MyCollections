package main.it.unibz.MyCollections;

import javafx.beans.property.SimpleBooleanProperty;
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
 * Created by claudio on 30/03/2017.
 */
public class User {
    private SimpleStringProperty username;
    private String passwordHash;
    private BufferedImage bufImage;
    private Image image;
    private ImageView imageView;
    private int id;
    private SimpleBooleanProperty isAdmin = new SimpleBooleanProperty(false);


    public User() {
        this.username = new SimpleStringProperty();
        try {
            //InputStream st = this.getClass().getResourceAsStream("default_user.png");
            this.image = new Image("default_user.png", 48, 48, true, true);
            this.setBufImage(SwingFXUtils.fromFXImage(image, null));
            this.imageView = getImageView();


        } catch (IOException ex) {
            ex.printStackTrace();//TODO: logger
        }
    }

    public void delete() {

    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin.get();
    }

    public void setAdmin(boolean admin) {
        isAdmin.set(admin);
    }

    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BufferedImage getUserImage() {
        return bufImage;
    }

    public void setUserImage(BufferedImage userImage) {
        this.bufImage = userImage;
    }

    public Image getImage() {
        return image;
    }

    public byte[] getUserImageArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public void setBufImage(BufferedImage bufImage) throws IOException {
        this.bufImage = bufImage;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (bufImage == null) return;
        ImageIO.write(bufImage, "jpg", os);

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        this.image = new Image(is, 128, 128, true, true);//SwingFXUtils.toFXImage(bufImage, null);
        //new Image(is, 128, 128, true, true);
        this.imageView = getImageView();
    }

    public ImageView getImageView() throws IOException {
        return new ImageView(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getUsername();
    } //TODO: write more!

    public boolean isEmpty() {
        if (username != null && !username.getValue().trim().equals("")) return false;
        if (passwordHash != null && !passwordHash.trim().equals("")) return false;
        return true;
    }
}
