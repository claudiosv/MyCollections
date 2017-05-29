package main.it.unibz.MyCollections;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Created by claudio on 29/05/2017.
 */
public class EditRecordView extends RecordView {

    public EditRecordView(Record passRecord)
    {
        super(passRecord);
        Button saveButton = new Button("Save");
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
