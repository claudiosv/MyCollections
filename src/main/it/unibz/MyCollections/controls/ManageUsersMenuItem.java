package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.views.AboutView;
import main.it.unibz.MyCollections.views.ManageUsersView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Claudio on 06/06/2017.
 */
public class ManageUsersMenuItem  extends MenuItem implements AdvancedCustomMenuItem {
    public ManageUsersMenuItem(Stage primaryStage) {
        super("Manage Users");
        this.setOnAction((event -> new ManageUsersView(primaryStage)));
        this.setGraphic(new ImageView(new Image("user.png")));
    }

    @Override
    public boolean isAdminOnly() {
        return true;
    }

    @Override
    public boolean isDataOnly() {
        return true;
    }

    @Override
    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }
}
