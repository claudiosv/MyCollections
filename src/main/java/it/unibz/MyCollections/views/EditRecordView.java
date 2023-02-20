package it.unibz.MyCollections.views;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import it.unibz.MyCollections.AddressRecord;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to edit record in collection. The purpose of this class
 * is to add the appropriate "Save" button to the abstract
 * class RecordView, allowing the user to save the changes
 * they made on a Record.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see it.unibz.MyCollections.views.RecordView
 * @since 1.0
 */
class EditRecordView extends RecordView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(EditRecordView.class.getName());

    /**
     * Instantiates this edit record view. This creates the necessary
     * controls and adds them to the stage.
     *
     * @param passRecord  previously instantiated record to be added.
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    public EditRecordView(AddressRecord passRecord, Stage parentStage) {
        super(passRecord, parentStage);
        dialog.setTitle("Edit Record");
        logger.entering(getClass().getName(), "EditRecordView");
        dialog.getIcons().add(new Image("address-book-pencil.png"));
        Button saveButton = new Button("Save");
        saveButton.setGraphic(new ImageView(new Image("disk-black.png")));
        saveButton.setOnAction(event -> {
            logger.log(Level.INFO, "Save button clicked");
            passRecord.setFirstName(firstNameTxt.getText());
            passRecord.setLastName(lastNameTxt.getText());
            passRecord.setCompanyName(companyNameTxt.getText());
            passRecord.setAddress(addressTxt.getText());
            passRecord.setTelephoneNumber(telephoneNumberTxt.getText());
            passRecord.setEmailAddress(emailAddressTxt.getText());
            dialog.hide();
        });
        grid.add(saveButton, 1, 7);
        logger.exiting(getClass().getName(), "EditRecordView");
    }
}
