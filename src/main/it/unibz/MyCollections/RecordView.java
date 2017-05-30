package main.it.unibz.MyCollections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by claudio on 29/05/2017.
 */
public class RecordView {
    GridPane grid;
    Stage dialog;
    TextField firstNameTxt;
    TextField lastNameTxt;
    TextField companyNameeTxt;
    TextField addressTxt;
    TextField telephoneNumbeTxt;
    TextField emailAddressTxt;
    Record record;
    public RecordView(Record record, Stage parentStage)
    {
        this.record = record;
        dialog = new Stage();
        dialog.setTitle("JavaFX Welcome");
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 300, 350);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        try
        {
            ImageView imageView = record.getImageView();
            imageView.setFitHeight(64);
            imageView.setFitWidth(64);
            grid.add(imageView, 0, 0);
            Button openButton = new Button("Open file...");
            openButton.setGraphic(new ImageView(new Image("blue-folder-open-image.png")));
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Profile Picture");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("All Images", "*.*")
            );

            openButton.setOnAction((event) -> {
                File file = fileChooser.showOpenDialog(dialog);
                if (file != null) {
                    try {
                        record.setBufImage(ImageIO.read(file));
                        imageView.setImage(record.getImage());
                    }catch (Exception e){}
                    //openFile(file);
                }
            });
            grid.add(openButton, 1, 0);

        }catch (Exception ex){}


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
        companyNameeTxt = new TextField(record.getCompanyName());
        grid.add(companyNameeTxt, 1, 3);

        Label addressLbl = new Label("Address:");
        grid.add(addressLbl, 0, 4);
        addressTxt = new TextField(record.getAddress());
        grid.add(addressTxt, 1, 4);

        Label telephoneNumberLbl = new Label("Telephone:");
        grid.add(telephoneNumberLbl, 0, 5);
        telephoneNumbeTxt = new TextField(record.getTelephoneNumber());
        grid.add(telephoneNumbeTxt, 1, 5);

        Label emailAddressLbl = new Label("Email Address:");
        grid.add(emailAddressLbl, 0, 6);
        emailAddressTxt = new TextField(record.getEmailAddress());
        grid.add(emailAddressTxt, 1, 6);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);

    }


   // public Record getRecord()
  //  {
 //       return record;
  //  }

    public Record show()
    {
        dialog.showAndWait();
        return record;
    }
}
