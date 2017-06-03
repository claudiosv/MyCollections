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
import main.it.unibz.MyCollections.User;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by Claudio on 02/06/2017.
 */
public class UserView {
    GridPane grid;
    Stage dialog;
    TextField usernameTxt;
    PasswordField passwordField;
    PasswordField passwordFieldConf;
    CheckBox adminCheckbox;
    User user;

    public UserView(User user, Stage parentStage) {
        this.user = user;
        dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 300, 350);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        try {
            ImageView imageView = user.getImageView();
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
                        user.setBufImage(ImageIO.read(file));
                        imageView.setImage(user.getImage());
                    } catch (Exception e) {
                    }
                    //openFile(file);
                }
            });
            grid.add(openButton, 1, 0);

        } catch (Exception ex) {
        }


        Label firstNameLbl = new Label("Username:");
        grid.add(firstNameLbl, 0, 1);
        usernameTxt = new TextField(user.getUsername());
        grid.add(usernameTxt, 1, 1);

        Label lastNameLbl = new Label("Password:");
        grid.add(lastNameLbl, 0, 2);
        passwordField = new PasswordField();
        passwordField.setPromptText("Blank = no change");
        grid.add(passwordField, 1, 2);

        passwordFieldConf = new PasswordField();
        passwordFieldConf.setPromptText("Confirm");
        grid.add(passwordFieldConf, 1, 3);

        Label emailAddressLbl = new Label("Privilege:");
        grid.add(emailAddressLbl, 0, 4);


        adminCheckbox = new CheckBox("Administrator");
        adminCheckbox.setSelected(user.isAdmin());
        grid.add(adminCheckbox, 1, 4);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);

    }


    // public Record getRecord()
    //  {
    //       return record;
    //  }

    public User show() {
        dialog.showAndWait();
        return user;
    }
}
