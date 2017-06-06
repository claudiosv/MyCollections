package main.it.unibz.MyCollections.controls;

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
public class ImportDataMenuItem extends MenuItem  implements AdvancedCustomMenuItem {
    public ImportDataMenuItem(Stage primaryStage) {
        super("Import");
        this.setGraphic(new ImageView(new Image("card-import.png")));
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
