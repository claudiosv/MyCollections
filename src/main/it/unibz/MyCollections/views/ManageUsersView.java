package main.it.unibz.MyCollections.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.Record;

import java.io.File;

/**
 * Created by claudio on 31/05/2017.
 */
public class ManageUsersView {
    public ManageUsersView(Stage parentStage)
    {
        Stage dialog = new Stage();
        dialog.setTitle("Manage Users");
        //all properties of user
        //add user button
        //context menu with add/delete/copy/edit
        //edit user
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 250, 200);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Label filePathLabel = new Label("Path to file:");
        grid.add(filePathLabel, 0, 0, 2, 1);
        TextField filePath = new TextField();
        filePath.setPrefWidth(300);
        grid.add(filePath, 0, 1, 1, 1);
        Button browseBtn = new Button("...");
        browseBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv"),
                    new FileChooser.ExtensionFilter("XML", "*.xml"),
                    new FileChooser.ExtensionFilter("SQLite DB", "*.db"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File file = fileChooser.showOpenDialog(parentStage);
            if (file != null) {
                if(file.getName().endsWith("csv"))
                {
                    fileTypeCombo.getSelectionModel().select(0);
                } else if(file.getName().endsWith("xml"))
                {
                    fileTypeCombo.getSelectionModel().select(1);
                } else  if(file.getName().endsWith("db")) {
                    fileTypeCombo.getSelectionModel().select(2);
                }
            }});
        grid.add(browseBtn, 1, 1, 1, 1);

        Label fileTypeLabel = new Label("File type:");
        fileTypeLabel.setPrefWidth(200);
        grid.add(fileTypeLabel, 0, 2, 2, 1);

        fileTypeCombo.getItems().addAll("Comma-Separated Values", "Extensible Markup Language", "SQLite Database");
        grid.add(fileTypeCombo, 0, 3, 2, 1);

        Button btn = new Button("Close");
        btn.setGraphic(new ImageView(new Image("cross-button.png")));
        btn.setOnAction((event) -> {
            dialog.hide();
        });

        Button btnExport = new Button("Import");
        btnExport.setGraphic(new ImageView(new Image("card-import.png")));
        btnExport.setOnAction((event) -> {
            dialog.hide();
        });
        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn, btnExport);

        grid.add(hbBtn, 0, 4, 2, 1);


        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

}
