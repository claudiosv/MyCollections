package it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class creates the Export Data Menu Item, allowing the user to export
 * his/her data to a file of their choice.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ExportDataMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Constructs {@link ExportDataMenuItem} by setting the MenuItem's graphic.
     * The action is set later down the execution chain once a user and data is known.
     *
     * @param primaryStage The primary stage of the JavaFX Application.
     */
    public ExportDataMenuItem(Stage primaryStage) {
        super("Export");
        this.setGraphic(new ImageView(new Image("address-book-arrow.png")));
    }

    /**
     * Gets whether this <code>MenuItem</code> is only visible to admins.
     * This ensures the menu item will be hidden to non-admin users.
     *
     * @return <code>false</code> to represent that {@link ExportDataMenuItem} is visible to everyone.
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Gets whether this <code>MenuItem</code> is dependant on data being loaded.
     * This ensures the menu item will be hidden from non-logged in users.
     *
     * @return <code>true</code> to represent that {@link ExportDataMenuItem} is visible only when a user is logged in.
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
