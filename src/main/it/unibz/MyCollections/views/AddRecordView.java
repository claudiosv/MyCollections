package main.it.unibz.MyCollections.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.Record;
import main.it.unibz.MyCollections.SQLiteHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 29/05/2017.
 */
public class AddRecordView extends RecordView {
    private static final Logger logger = Logger.getLogger(AddRecordView.class.getName());

    public AddRecordView(Record passRecord, Stage parentStage) {
        super(passRecord, parentStage);
        logger.entering(getClass().getName(), "AddRecordView");
        Button saveButton = new Button("Add");
        saveButton.setGraphic(new ImageView(new Image("plus-button.png")));
        saveButton.setOnAction((event) -> {
            logger.log(Level.INFO, "Add button clicked");
            record.setFirstName(firstNameTxt.getText());
            record.setLastName(lastNameTxt.getText());
            record.setCompanyName(companyNameTxt.getText());
            record.setAddress(addressTxt.getText());
            record.setTelephoneNumber(telephoneNumberTxt.getText());
            record.setEmailAddress(emailAddressTxt.getText());
            dialog.hide();
        });
        grid.add(saveButton, 1, 7);
        logger.exiting(getClass().getName(), "AddRecordView");
    }


}
