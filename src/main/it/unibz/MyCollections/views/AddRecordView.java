package main.it.unibz.MyCollections.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.Record;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to add records to collection. This view extends the
 * abstract view RecordView, which contains all the fields necessary
 * to edit or add a record. This class merely adds an "Add" button
 * and the necessary logic to drive it.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see main.it.unibz.MyCollections.views.RecordView
 * @since 1.0
 */
class AddRecordView extends RecordView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(AddRecordView.class.getName());

    /**
     * Instantiates the add record view.
     *
     * @param passRecord  previously instantiated record to be added.
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    public AddRecordView(Record passRecord, Stage parentStage) {
        super(passRecord, parentStage);
        logger.entering(getClass().getName(), "AddRecordView");
        dialog.setTitle("Add Record");
        dialog.getIcons().add(new Image("address-book-plus.png"));
        Button saveButton = new Button("Add");
        saveButton.setGraphic(new ImageView(new Image("address-book-plus.png")));
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
