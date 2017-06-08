package main.it.unibz.MyCollections.views;

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
import main.it.unibz.MyCollections.Record;
import main.it.unibz.MyCollections.portability.Exporter;
import main.it.unibz.MyCollections.portability.ExporterFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to show export data function. The purpose of this class
 * is to allow the user to easily export records
 * to a CSV or XML file.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
class ExportDataView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ExportDataView.class.getName());

    /**
     * Instantiates an export user view. Creates the necessary
     * controls to allow a user to export the records.
     * Accepts a list of records to export.
     *
     * @param parentStage Stage from which constructor is called.
     * @param records     list of records to export.
     * @author Claudio Spiess
     */
    public ExportDataView(Stage parentStage, List<Record> records) {
        logger.entering(getClass().getName(), "ExportDataView");
        Stage dialog = new Stage();

        dialog.getIcons().add(new Image("address-book-arrow.png"));
        dialog.setTitle("Export Data");
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
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("CSV", "*.csv"),
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File file = fileChooser.showSaveDialog(parentStage);
            if (file.getPath() != null) {
                logger.log(Level.INFO, "File selected: {0}", file.getPath());
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

        Button btnExport = new Button("Export");
        btnExport.setGraphic(new ImageView(new Image("address-book-arrow.png")));
        btnExport.setOnAction((event) -> {
            logger.log(Level.INFO, "Export button clicked: {0}", fileTypeCombo.getValue());
            Path file = new File(filePath.getText()).toPath();
            switch (fileTypeCombo.getSelectionModel().getSelectedIndex()) {
                case 0:
                    Exporter csvExport = new ExporterFactory().getExporter("csv");
                    csvExport.exportRecords(records, file);

                    Alert csvAlert = new Alert(Alert.AlertType.INFORMATION);
                    csvAlert.setTitle("Export Successful");
                    csvAlert.setHeaderText("Export Successful");
                    csvAlert.setContentText(records.size() + " records successfully exported to CSV file: " + file.toString());
                    csvAlert.showAndWait();
                    logger.log(Level.INFO, "Records exported: {0}", file);
                    break;
                case 1:
                    Exporter xmlExporter = new ExporterFactory().getExporter("xml");
                    xmlExporter.exportRecords(records, file);

                    Alert xmlAlert = new Alert(Alert.AlertType.INFORMATION);
                    xmlAlert.setTitle("Export Successful");
                    xmlAlert.setHeaderText("Export Successful");
                    xmlAlert.setContentText(records.size() + " records successfully exported to XML file: " + file.toString());
                    xmlAlert.showAndWait();
                    logger.log(Level.INFO, "Records exported: {0}", file);
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
        dialog.showAndWait();
        logger.exiting(getClass().getName(), "ExportDataView");
    }
}
