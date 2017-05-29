package main.it.unibz.MyCollections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by claudio on 29/05/2017.
 */
public class RecordView {
    public RecordView()
    {

    }
    public RecordView(Record record)
    {
        Stage dialog = new Stage();
        Scene scene = new Scene(new VBox(), 300, 350);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        try
        {
            ImageView imageView = record.getImageView();
            grid.add(imageView, 0, 0);
        }catch (Exception ex){}


        Label firstNameLbl = new Label("First name:");
        grid.add(firstNameLbl, 0, 1);
        TextField firstNameTxt = new TextField(record.getFirstName());
        grid.add(firstNameTxt, 1, 1);

        Label lastNameLbl = new Label("Last name:");
        grid.add(lastNameLbl, 0, 2);
        TextField lastNameTxt = new TextField(record.getLastName());
        grid.add(lastNameTxt, 1, 2);

        Label companyNameLbl = new Label("Company:");
        grid.add(companyNameLbl, 0, 3);
        TextField companyNameeTxt = new TextField(record.getCompanyName());
        grid.add(companyNameeTxt, 1, 3);

        Label addressLbl = new Label("Address:");
        grid.add(addressLbl, 0, 4);
        TextField addressTxt = new TextField(record.getAddress());
        grid.add(addressTxt, 1, 4);

        Label telephoneNumberLbl = new Label("Telephone:");
        grid.add(telephoneNumberLbl, 0, 5);
        TextField telephoneNumbeTxt = new TextField(record.getTelephoneNumber());
        grid.add(telephoneNumbeTxt, 1, 5);

        Label emailAddressLbl = new Label("Email Address:");
        grid.add(emailAddressLbl, 0, 6);
        TextField emailAddressTxt = new TextField(record.getEmailAddress());
        grid.add(emailAddressTxt, 1, 6);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        dialog.show();
    }
}
