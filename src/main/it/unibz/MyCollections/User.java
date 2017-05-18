package main.it.unibz.MyCollections;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by claudio on 30/03/2017.
 */
public class User {
    private String username;
    private String passwordHash;
    private BufferedImage userImage;
    private int id;

    public void delete()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BufferedImage getUserImage() {
        return userImage;
    }

    public byte[] getUserImageArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(userImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }


    public void setUserImage(BufferedImage userImage) {
        this.userImage = userImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
