package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.views.AboutView;
import main.it.unibz.MyCollections.views.DataSummaryView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Claudio on 06/06/2017.
 */
public class DataSummaryMenuItem extends MenuItem implements AdvancedCustomMenuItem {
    public DataSummaryMenuItem(Stage primaryStage) {
        super("Summary of data");
        this.setOnAction(event -> new DataSummaryView(primaryStage));
        this.setGraphic(new ImageView(new Image("dashboard.png")));
    }

    @Override
    public boolean isAdminOnly() {
        return false;
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
