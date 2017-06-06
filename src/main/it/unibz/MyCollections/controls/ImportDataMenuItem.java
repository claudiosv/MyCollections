package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ImportDataMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ImportDataMenuItem(Stage primaryStage) {
        super("Import");
        this.setGraphic(new ImageView(new Image("card-import.png")));
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
        return true;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void setVisible() {
        this.setVisible(true);
    }
}
