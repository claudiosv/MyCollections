package main.it.unibz.MyCollections.views;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.Record;
import main.it.unibz.MyCollections.SQLiteHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/** View to edit record in collection.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 * @see main.it.unibz.MyCollections.views.RecordView
 */
public class EditRecordView extends RecordView {
    private static final Logger logger = Logger.getLogger(EditRecordView.class.getName());

    /** Instantiates this edit record view.
     *
     * @author Claudio Spiess
     * @param passRecord previously instantiated record to be added.
     * @param parentStage  Stage from which constructor is called.
     */
    public EditRecordView(Record passRecord, Stage parentStage) {
        super(passRecord, parentStage);
        dialog.setTitle("Edit Record");
        logger.entering(getClass().getName(), "EditRecordView");
        Button saveButton = new Button("Save");
        saveButton.setGraphic(new ImageView(new Image("disk-black.png")));
        saveButton.setOnAction(event -> {
            logger.log(Level.INFO, "Save button clicked");
            record.setFirstName(firstNameTxt.getText());
            record.setLastName(lastNameTxt.getText());
            record.setCompanyName(companyNameTxt.getText());
            record.setAddress(addressTxt.getText());
            record.setTelephoneNumber(telephoneNumberTxt.getText());
            record.setEmailAddress(emailAddressTxt.getText());
            dialog.hide();
        });
        grid.add(saveButton, 1, 7);
        logger.exiting(getClass().getName(), "EditRecordView");
    }
}
