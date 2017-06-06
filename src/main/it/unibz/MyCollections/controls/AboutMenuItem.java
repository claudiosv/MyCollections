package main.it.unibz.MyCollections.controls;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.views.AboutView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class AboutMenuItem extends MenuItem implements AdvancedCustomMenuItem {

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public AboutMenuItem(Stage primaryStage) {
        super("About");
        this.setOnAction(event ->
            new AboutView(primaryStage)
        );
        this.setGraphic(new ImageView(new Image("information-button.png")));
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public boolean isDataOnly() {
        return false;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }
}
