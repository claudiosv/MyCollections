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
 * Created by Claudio on 06/06/2017.
 */
public class AboutMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    public AboutMenuItem(Stage primaryStage) {
        super("About");
        this.setOnAction(event ->
            new AboutView(primaryStage)
        );
        this.setGraphic(new ImageView(new Image("information-button.png")));
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
