package main.it.unibz.MyCollections;

import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by claudio on 29/05/2017.
 */
public class AddRecordView extends RecordView {
    public AddRecordView(Record passRecord, Stage parentStage)
    {
        super(passRecord, parentStage);
        Button saveButton = new Button("Add");
        saveButton.setOnAction((event) -> {
            record.setFirstName(firstNameTxt.getText());
            record.setLastName(lastNameTxt.getText());
            record.setCompanyName(companyNameeTxt.getText());
            record.setAddress(addressTxt.getText());
            record.setTelephoneNumber(telephoneNumbeTxt.getText());
            record.setEmailAddress(emailAddressTxt.getText());
            dialog.hide();
        });
        grid.add(saveButton, 1, 7);
    }


}
