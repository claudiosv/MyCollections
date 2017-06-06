package main.it.unibz.MyCollections.controls;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import main.it.unibz.MyCollections.DatabaseSession;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ExitMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ExitMenuItem() {
        super("Exit");
        this.setGraphic(new ImageView(new Image("cross-button.png")));
        this.setOnAction(t -> {
            DatabaseSession.getInstance().save();
            Platform.exit();
            System.exit(0);
        });
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
