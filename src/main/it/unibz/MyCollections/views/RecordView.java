package main.it.unibz.MyCollections.views;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.Record;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract view to manage a record. This class cannot
 * be instantiated since it does not have buttons to Save
 * or Add a record.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see Record
 * @see AddRecordView
 * @see EditRecordView
 * @since 1.0
 */
abstract class RecordView {
    private static final Logger logger = Logger.getLogger(RecordView.class.getName());
    final GridPane grid;
    final Stage dialog;
    final TextField firstNameTxt;
    final TextField lastNameTxt;
    final TextField companyNameTxt;
    final TextField addressTxt;
    final TextField telephoneNumberTxt;
    final TextField emailAddressTxt;
    final Record record;

    /**
     * Instantiates this record view. Adds the controls
     * needed for to display the image, labels, and fields.
     *
     * @param record      previously instantiated record to be added.
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    RecordView(Record record, Stage parentStage) {
        logger.entering(getClass().getName(), "RecordView");
        this.record = record;
        dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 300, 420);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        ImageView imageView = new ImageView();
        imageView.setFitHeight(128);
        imageView.setFitWidth(128);
        imageView.setImage(record.getImage());
        grid.add(imageView, 0, 0);
        Button openButton = new Button("Open file...");

        openButton.setGraphic(new ImageView(new Image("blue-folder-open-image.png")));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Profile Picture");
        fileChooser.setInitialDirectory(new File("C:\\Users\\claudio\\Downloads\\staff")); //TODO: fix
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );

        openButton.setOnAction(event -> {
            logger.log(Level.INFO, "Browsing for file");
            File file = fileChooser.showOpenDialog(dialog);
            if (file != null) {
                try {
                    record.setImage(SwingFXUtils.toFXImage(ImageIO.read(file), null));
                    imageView.setImage(record.getImage());
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "IO error loading image", ex); //TODO: add dialog
                }
            }
        });
        grid.add(openButton, 1, 0);


        Label firstNameLbl = new Label("First name:");
        grid.add(firstNameLbl, 0, 1);
        firstNameTxt = new TextField(record.getFirstName());
        grid.add(firstNameTxt, 1, 1);

        Label lastNameLbl = new Label("Last name:");
        grid.add(lastNameLbl, 0, 2);
        lastNameTxt = new TextField(record.getLastName());
        grid.add(lastNameTxt, 1, 2);

        Label companyNameLbl = new Label("Company:");
        grid.add(companyNameLbl, 0, 3);
        companyNameTxt = new TextField(record.getCompanyName());
        grid.add(companyNameTxt, 1, 3);

        Label addressLbl = new Label("Address:");
        grid.add(addressLbl, 0, 4);
        addressTxt = new TextField(record.getAddress());
        grid.add(addressTxt, 1, 4);

        Label telephoneNumberLbl = new Label("Telephone:");
        grid.add(telephoneNumberLbl, 0, 5);
        telephoneNumberTxt = new TextField(record.getTelephoneNumber());
        grid.add(telephoneNumberTxt, 1, 5);

        Label emailAddressLbl = new Label("Email Address:");
        grid.add(emailAddressLbl, 0, 6);
        emailAddressTxt = new TextField(record.getEmailAddress());
        grid.add(emailAddressTxt, 1, 6);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        logger.exiting(getClass().getName(), "RecordView");
    }


    /**
     * Shows the record view and returns the edited or added record.
     *
     * @return Record that has been edited or added
     * @author Claudio Spiess
     */
    public Record show() {
        logger.entering(getClass().getName(), "show");
        dialog.showAndWait();
        return record;
    }
}
