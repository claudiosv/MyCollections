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
 * Created by Claudio on 06/06/2017.
 */
public class ExitMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    public ExitMenuItem() {
        super("Exit");
        this.setGraphic(new ImageView(new Image("cross-button.png")));
        this.setOnAction(t -> {
            DatabaseSession.getInstance().save();
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public boolean isAdminOnly() {
        return false;
    }

    @Override
    public boolean isDataOnly() {
        return false;
    }

    @Override
    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }
}
