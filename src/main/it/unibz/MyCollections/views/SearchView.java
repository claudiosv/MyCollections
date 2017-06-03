package main.it.unibz.MyCollections.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.Record;
import main.it.unibz.MyCollections.RecordSearchQuery;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by claudio on 30/05/2017.
 */
public class SearchView {
    GridPane grid;
    Stage dialog;
    TextField firstNameTxt;
    TextField lastNameTxt;
    TextField companyNameTxt;
    TextField addressTxt;
    TextField telephoneNumberTxt;
    TextField emailAddressTxt;
    RadioButton rb2;
    RadioButton rb1;

    public SearchView(Stage parentStage)
    {
        dialog = new Stage();
        dialog.setTitle("Search Records");
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 300, 350);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label info = new Label("Use asterisk (*) as wildcard");
        grid.add(info, 0, 0, 2, 1);

        Label firstNameLbl = new Label("First name:");
        grid.add(firstNameLbl, 0, 1);
        firstNameTxt = new TextField();
        grid.add(firstNameTxt, 1, 1);

        Label lastNameLbl = new Label("Last name:");
        grid.add(lastNameLbl, 0, 2);
        lastNameTxt = new TextField();
        grid.add(lastNameTxt, 1, 2);

        Label companyNameLbl = new Label("Company:");
        grid.add(companyNameLbl, 0, 3);
        companyNameTxt = new TextField();
        grid.add(companyNameTxt, 1, 3);

        Label addressLbl = new Label("Address:");
        grid.add(addressLbl, 0, 4);
        addressTxt = new TextField();
        grid.add(addressTxt, 1, 4);

        Label telephoneNumberLbl = new Label("Telephone:");
        grid.add(telephoneNumberLbl, 0, 5);
        telephoneNumberTxt = new TextField();
        grid.add(telephoneNumberTxt, 1, 5);

        Label emailAddressLbl = new Label("Email Address:");
        grid.add(emailAddressLbl, 0, 6);
        emailAddressTxt = new TextField();
        grid.add(emailAddressTxt, 1, 6);

        final ToggleGroup group = new ToggleGroup();

        rb1 = new RadioButton("Exclusive (This and this...)");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        grid.add(rb1, 0, 7, 2, 1);

        rb2 = new RadioButton("Inclusive (This or this...)");
        rb2.setToggleGroup(group);

        grid.add(rb2, 0, 8, 2, 1);

        Button saveButton = new Button("Search");
        saveButton.setGraphic(new ImageView(new Image("magnifier.png")));
        saveButton.setOnAction((event) -> {
            dialog.hide();
        });
        saveButton.setPrefWidth(150);
        grid.add(saveButton, 1, 9);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);

    }

    public RecordSearchQuery show()
    {
        dialog.showAndWait();
        RecordSearchQuery query = new RecordSearchQuery();
        query.setAddress(addressTxt.getText());
        query.setCompanyName(companyNameTxt.getText());
        query.setEmailAddress(emailAddressTxt.getText());
        query.setFirstName(firstNameTxt.getText());
        query.setLastName(lastNameTxt.getText());
        query.setTelephoneNumber(telephoneNumberTxt.getText());
        query.setExclusive(rb1.isSelected());
        return query;
    }
}
