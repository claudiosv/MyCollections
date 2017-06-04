package main.it.unibz.MyCollections.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.DatabaseSession;
import main.it.unibz.MyCollections.SQLiteHandler;
import main.it.unibz.MyCollections.Session;
import main.it.unibz.MyCollections.exceptions.UserAlreadyExistsException;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 30/05/2017.
 */
public class DataSummaryView {
    private static final Logger logger = Logger.getLogger(DataSummaryView.class.getName());

    public DataSummaryView(Stage parentStage) {
        logger.entering(getClass().getName(), "DataSummaryView");
        Stage dialog = new Stage();
        dialog.setTitle("Summary of data");
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 550, 250);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label dataF = new Label("File path to the data file:");
        grid.add(dataF, 0, 0);
        Label path = new Label(Paths.get(System.getProperty("user.dir"), "test.db").toString()); //TODO: rewrite where db is saved
        path.setWrapText(true);
        grid.add(path, 1, 0);

        Label number = new Label("Total number of data records:");
        grid.add(number, 0, 1);
        try {
            Label number1 = new Label(Integer.toString(DatabaseSession.getInstance().getRecordCount()));
            grid.add(number1, 1, 1);
        }  catch (SQLException ex) {
            //TODO: add dialogs for these errors
            logger.log(Level.SEVERE, "SQL error loading record count", ex);
        }
        Label number2 = new Label("Total number of added records:");
        grid.add(number2, 0, 2);
        Label number3 = new Label(Integer.toString(Session.getRecordsAdded()));
        grid.add(number3, 1, 2);

        Label number4 = new Label("Total number of deleted records:");
        grid.add(number4, 0, 3);
        Label number5 = new Label(Integer.toString(Session.getRecordsDeleted()));
        grid.add(number5, 1, 3);

        Button btn = new Button("Close");
        btn.setGraphic(new ImageView(new Image("cross-button.png")));
        btn.setOnAction((event) -> {
            logger.log(Level.INFO, "Closing view");
            dialog.hide();
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);

        grid.add(hbBtn, 1, 4);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        dialog.showAndWait();
        logger.exiting(getClass().getName(), "DataSummaryView");
    }
}
