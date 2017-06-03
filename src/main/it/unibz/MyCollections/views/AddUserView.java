package main.it.unibz.MyCollections.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.*;

/**
 * Created by Claudio on 02/06/2017.
 */
public class AddUserView extends UserView {
    public AddUserView(User user1, Stage parentStage) {
        super(user1, parentStage);
        Button saveButton = new Button("Add");
        saveButton.setGraphic(new ImageView(new Image("plus-button.png")));
        saveButton.setOnAction((event) -> {
            user.setUsername(usernameTxt.getText());
            if (passwordField.getText().equals(passwordFieldConf.getText()) && Validator.isValidPassword(passwordField.getText())) {
                HasherFactory hasherFactory = new HasherFactory();
                user.setPassword(hasherFactory.getHasher("sha512").hash(passwordField.getText()));
            }

            user.setAdmin(adminCheckbox.selectedProperty().get());
            try {
                DatabaseHandler.getInstance().addUser(user);
            } catch (Exception ex) {
                ex.printStackTrace(); //TODO: logger
            }
            dialog.hide();
        });
        grid.add(saveButton, 1, 5);
    }
}
