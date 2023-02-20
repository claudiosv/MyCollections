package it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class represents the Import Data menu item. This allows the user to import data
 * into their collection.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ImportDataMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Constructs an {@link ImportDataMenuItem} by setting the text and graphic.
     *
     * @param primaryStage The primary stage of the JavaFX Application.
     */
    public ImportDataMenuItem(Stage primaryStage) {
        super("Import");
        this.setGraphic(new ImageView(new Image("card-import.png")));
    }

    /**
     * Gets whether this <code>MenuItem</code> is only visible to admins.
     * This ensures the menu item will be visible to non-admin users.
     *
     * @return <code>false</code> to represent that {@link ImportDataMenuItem} is visible to everyone.
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Gets whether this <code>MenuItem</code> is only visible to logged in users.
     * This ensures the menu item will be visible to non-admin users.
     *
     * @return <code>true</code> to represent that {@link ImportDataMenuItem} is visible to non admin users.
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
