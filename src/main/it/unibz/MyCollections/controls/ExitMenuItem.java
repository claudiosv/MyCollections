package main.it.unibz.MyCollections.controls;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.it.unibz.MyCollections.DatabaseSession;

/**
 * This class creates a {@link MenuItem} intended for Exiting the application.
 * It sets the text, graphic, and action needed.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class ExitMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    /**
     * Constructs a {@link ExitMenuItem} by setting the right properties
     * of the {@link MenuItem}.
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
     * Gets whether this <code>MenuItem</code> is visible to admins only.
     * This ensures the menu item will be visible to non-admin users.
     *
     * @return <code>false</code> to represent that {@link ExitMenuItem} is visible to everyone.
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Gets whether this <code>MenuItem</code> is dependant on data being loaded.
     * This ensures the menu item will be hidden from non-logged in users.
     *
     * @return <code>false</code> to represent that {@link ExitMenuItem} is visible only when a user is logged in.
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
