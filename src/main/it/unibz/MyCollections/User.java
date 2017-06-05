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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 30/03/2017.
 */
public class User {
    private SimpleStringProperty username;
    private String passwordHash;
    private Image image;
    private ImageView imageView;
    private int id;
    private SimpleBooleanProperty isAdmin = new SimpleBooleanProperty(false);
    private static final Logger logger = Logger.getLogger(User.class.getName());

    public User() {
        this.username = new SimpleStringProperty();
            this.image = new Image("default_user.png", 48, 48, true, true);
            imageView = new ImageView(image);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
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
