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

/** Represents a user.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class User {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private SimpleStringProperty username = new SimpleStringProperty();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private String passwordHash;

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
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private int id;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private SimpleBooleanProperty isAdmin = new SimpleBooleanProperty(false);

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private static final Logger logger = Logger.getLogger(User.class.getName());

    /**
     * Instantiates User object. Sets the default image of the user
     * and creates a JavaFX image view.
     *
     * @author Claudio Spiess
     */
    public User() {
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
    public SimpleStringProperty usernameProperty() {
        return username;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public boolean isAdmin() {
        return isAdmin.get();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setAdmin(boolean admin) {
        isAdmin.set(admin);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getUsername() {
        return username.getValue();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setUsername(String username) {
        this.username.set(username);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
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
     * Writes User's image into a byte array for IO operations.
     *
     * @author Claudio Spiess
     * @return Bytes of the Record's image.
     */
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

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Converts username of User into a string.
     *
     * @author Claudio Spiess
     * @return String of the User's username.
     */
    @Override
    public String toString() {
        return this.getUsername();
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public boolean isEmpty() {
        if (username != null && !username.getValue().trim().equals("")) return false;
        if (passwordHash != null && !passwordHash.trim().equals("")) return false;
        return true;
    }
}
