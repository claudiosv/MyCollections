package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.views.AboutView;

/**
 * Menu item for the About view. This class
 * is an AdvancedCustomMenu item that acts as a
 * JavaFX MenuItem to be used in the MainMenuBuilder.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class AboutMenuItem extends MenuItem implements AdvancedCustomMenuItem {

    /**
     * Constructs AboutMenuItem by setting the MenuItem's graphic
     * and its action.
     *
     * @param primaryStage The primary stage of the JavaFX Application.
     */
    public AboutMenuItem(Stage primaryStage) {
        super("About");
        this.setOnAction(event ->
                new AboutView(primaryStage)
        );
        this.setGraphic(new ImageView(new Image("information-button.png")));
    }

    /**
     * Gets whether this <code>MenuItem</code> is restricted only to admin users.
     * This ensures the menu item will be visible to non-admin users too.
     *
     * @return <code>false</code> to represent that AboutMenuItem is visible to everyone.
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Gets whether this <code>MenuItem</code> is dependant on data being loaded.
     * This ensures the menu item will be visible to non-logged in users.
     *
     * @return <code>false</code> to represent that AboutMenuItem is visible at all times.
     */
    @Override
    public boolean isDataOnly() {
        return false;
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
