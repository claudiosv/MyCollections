package it.unibz.MyCollections.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import it.unibz.MyCollections.Importer;
import it.unibz.MyCollections.AddressRecord;
import it.unibz.MyCollections.portability.ImporterFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to import data. The purpose of this view
 * is to allow the user to easily import
 * data from a CSV or XML file into his records collection.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
class ImportDataView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ImportDataView.class.getName());

    /**
     * Holds the JavaFX dialog of this view. This is necessary to close the view later.
     */
    private final Stage dialog;

    /**
     * Holds the imported records. This is necessary to return the records later.
     */
    private List<AddressRecord> importedRecords = new ArrayList<>();

    /**
     * Instantiates this import data view. Creates the necessary controls
     * and provides the logic to load the file and pass it to the right
     * parser.
     *
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    public ImportDataView(Stage parentStage) {
        logger.entering(getClass().getName(), "ImportDataView");
        dialog = new Stage();
        dialog.getIcons().add(new Image("card-import-big.png"));
        dialog.setTitle("Import Data");
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 350, 200);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        ComboBox fileTypeCombo = new ComboBox();
        //noinspection unchecked
        fileTypeCombo.getItems().addAll("Comma-Separated Values", "Extensible Markup Language");

        Label filePathLabel = new Label("Path to file:");
        grid.add(filePathLabel, 0, 0, 2, 1);
        TextField filePath = new TextField();
        filePath.setPrefWidth(300);
        grid.add(filePath, 0, 1, 1, 1);
        Button browseBtn = new Button("...");
        browseBtn.setOnAction(event -> {
            logger.log(Level.INFO, "Browse button clicked");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("CSV", "*.csv"),
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File file = fileChooser.showOpenDialog(parentStage);
            if (file != null) {
                if (file.getName().endsWith("csv")) {
                    fileTypeCombo.getSelectionModel().select(0);
                } else if (file.getName().endsWith("xml")) {
                    fileTypeCombo.getSelectionModel().select(1);
                }
                filePath.setText(file.getPath());
            } else {
                logger.log(Level.INFO, "File null: {0}", file);
            }
        });
        grid.add(browseBtn, 1, 1, 1, 1);

        Label fileTypeLabel = new Label("File type:");
        fileTypeLabel.setPrefWidth(200);
        grid.add(fileTypeLabel, 0, 2, 2, 1);
        grid.add(fileTypeCombo, 0, 3, 2, 1);

        Button btn = new Button("Close");
        btn.setGraphic(new ImageView(new Image("cross-button.png")));
        btn.setOnAction(event -> {
            logger.log(Level.INFO, "Close button clicked");
            dialog.hide();
        });

        Button btnExport = new Button("Import");
        btnExport.setGraphic(new ImageView(new Image("card-import.png")));
        btnExport.setOnAction((event) -> {
            logger.log(Level.INFO, "Import button clicked: {0}", fileTypeCombo.getValue());
            Path file = new File(filePath.getText()).toPath();
            switch (fileTypeCombo.getSelectionModel().getSelectedIndex()) {
                case 0:
                    Importer csvImporter = new ImporterFactory().getImporter("csv");
                    importedRecords = csvImporter.importRecords(file);
                    dialog.hide();
                    Alert csvAlert = new Alert(Alert.AlertType.INFORMATION);
                    csvAlert.setTitle("Export Successful");
                    csvAlert.setHeaderText("Export Successful");
                    csvAlert.setContentText(importedRecords.size() + " records successfully imported from CSV file: " + file.toString());
                    csvAlert.showAndWait();
                    logger.log(Level.INFO, "Records imported");
                    break;

                case 1:
                    Importer xmlImporter = new ImporterFactory().getImporter("xml");
                    importedRecords = xmlImporter.importRecords(file);
                    dialog.hide();
                    Alert xmlAlert = new Alert(Alert.AlertType.INFORMATION);
                    xmlAlert.setTitle("Export Successful");
                    xmlAlert.setHeaderText("Export Successful");
                    xmlAlert.setContentText(importedRecords.size() + " records successfully imported from XML file: " + file.toString());
                    xmlAlert.showAndWait();
                    logger.log(Level.INFO, "Records imported");
                    break;
                default:
                    break;
            }
            dialog.hide();
        });
        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn, btnExport);

        grid.add(hbBtn, 0, 4, 2, 1);


        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        logger.exiting(getClass().getName(), "ImportDataView");
    }

    /**
     * Shows the dialog to import data. Once the user has
     * imported something, the method returns the list of
     * imported records to the caller.
     *
     * @return Returns list of imported records.
     * @author Claudio Spiess
     */
    public List<AddressRecord> show() {
        dialog.showAndWait();
        return importedRecords;
    }
}
