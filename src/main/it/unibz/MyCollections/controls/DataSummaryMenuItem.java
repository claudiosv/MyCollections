package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.views.DataSummaryView;

/**
 * Menu item for the {@link DataSummaryView} menu item. This class
 * ensures that the control text, graphic, and action are appropriate.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class DataSummaryMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Constructs {@link DataSummaryMenuItem} by setting the MenuItem's graphic
     * and its action.
     *
     * @param primaryStage The primary stage of the JavaFX Application.
     */
    public DataSummaryMenuItem(Stage primaryStage) {
        super("Summary of data");
        this.setOnAction(event -> new DataSummaryView(primaryStage));
        this.setGraphic(new ImageView(new Image("dashboard.png")));
    }

    /**
     * Gets whether this <code>MenuItem</code> is restricted only to admin users.
     * This ensures the menu item will be visible to non-admin users too.
     *
     * @return <code>false</code> to represent that {@link DataSummaryMenuItem} is visible to everyone.
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Gets whether this <code>MenuItem</code> is dependant on data being loaded.
     * This ensures the menu item will be hidden to non-logged in users.
     *
     * @return <code>true</code> to represent that {@link DataSummaryMenuItem} is visible only when a user is logged in.
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
