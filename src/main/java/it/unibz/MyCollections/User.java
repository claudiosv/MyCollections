package it.unibz.MyCollections;

import javafx.beans.property.SimpleBooleanProperty;
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
 * Represents a user. A User
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class User {
    /**
     * Holds the logger for this class.
     */
    private static final Logger logger = Logger.getLogger(User.class.getName());

    /**
     * Holds the username property of the user.
     */
    private final SimpleStringProperty username = new SimpleStringProperty();

    /**
     * Holds the admin property of the user.
     */
    private final SimpleBooleanProperty isAdmin = new SimpleBooleanProperty(false);

    /**
     * Holds the password hash string.
     */
    private String passwordHash;

    /**
     * Holds the user's image.
     */
    private Image image;

    /**
     * Holds the JavaFX {@link ImageView} of the user.
     */
    private ImageView imageView;

    /**
     * Holds the user id. This is the id of the user in the database.
     */
    private int id;


    /*
     * Sets the default image of the user and creates a JavaFX image view.
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
     * Gets the username. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return User's username property.
     */
    @SuppressWarnings("unused")
    public SimpleStringProperty usernameProperty() {
        return username;
    }

    /**
     * Gets the admin property. Required because JavaFX uses reflection to get columnNameProperty().
     *
     * @return User's admin property.
     */
    @SuppressWarnings("unused")
    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    /**
     * Gets whether the user is an admin or not.
     *
     * @return <code>true</code> if the user is an admin.
     */
    public boolean isAdmin() {
        return isAdmin.get();
    }

    /**
     * Sets whether a user is an admin or not.
     *
     * @param admin Whether the user is an admin or not.
     */
    public void setAdmin(boolean admin) {
        isAdmin.set(admin);
    }

    /**
     * Gets the username value.
     *
     * @return Username string.
     */
    public String getUsername() {
        return username.getValue();
    }

    /**
     * Sets the username.
     *
     * @param username New username value to set.
     */
    public void setUsername(String username) {
        this.username.set(username);
    }

    /**
     * Gets the password hash.
     *
     * @return Password hash string.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash of the user.
     *
     * @param passwordHash The new hash value to set.
     */
    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets the user's image.
     *
     * @return User's image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the user's image and builds an {@link ImageView}.
     *
     * @param image New image to set.
     */
    public void setImage(Image image) {
        this.image = image;
        imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
    }

    /**
     * Writes User's image into a byte array for IO operations.
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
     * Gets the user's id.
     *
     * @return User's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's id.
     *
     * @param id The new id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Converts username of User into a string.
     *
     * @return String of the User's username.
     * @author Claudio Spiess
     */
    @Override
    public String toString() {
        return this.getUsername();
    }

    /**
     * Checks whether the user is considered empty or not.
     *
     * @return <code>true</code> if the username and password are blank.
     */
    public boolean isEmpty() {
        if (username != null && !username.getValue().trim().equals("")) return false;
        return !(passwordHash != null && !passwordHash.trim().equals(""));
    }

    /**
     * Gets the image view to be used by the JavaFX table view.
     *
     * @return ImageView containing the User's image.
     */
    public ImageView getImageView() {
        return imageView;
    }
}
