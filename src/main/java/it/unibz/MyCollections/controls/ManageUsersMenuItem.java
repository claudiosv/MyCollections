package it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import it.unibz.MyCollections.views.ManageUsersView;

/**
 * This class represents the menu item to Manage Users.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ManageUsersMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Constructs a MangeUsersMenuItem by setting the text, action, and graphic.
     *
     * @param primaryStage The primary stage of the JavaFX Application.
     */
    public ManageUsersMenuItem(Stage primaryStage) {
        super("Manage Users");
        this.setOnAction((event -> new ManageUsersView(primaryStage)));
        this.setGraphic(new ImageView(new Image("user-business.png")));
    }

    /**
     * Gets whether this <code>MenuItem</code> is only visible to admins.
     * This ensures the menu item will be hidden to non-admin users.
     *
     * @return <code>true</code> to represent that {@link ManageUsersMenuItem} is visible only to admins.
     */
    @Override
    public boolean isAdminOnly() {
        return true;
    }

    /**
     * Gets whether this <code>MenuItem</code> is only visible to logged in users.
     * This ensures the menu item will be hidden to non-logged in users.
     *
     * @return <code>true</code> to represent that {@link ManageUsersMenuItem} is only visible to logged in users.
     */
    @Override
    public boolean isDataOnly() {
        return true;
    }

    /**
     * Sets the control's visibility. Makes parent class's setVisible function accessible.
     *
     * @param visible Whether the control should be visible or not.
     */
    @Override
    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }
}
