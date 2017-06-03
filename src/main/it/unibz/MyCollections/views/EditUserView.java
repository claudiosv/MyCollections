package main.it.unibz.MyCollections.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.User;

/**
 * Created by claudio on 31/05/2017.
 */
public class EditUserView extends UserView {
    public EditUserView(User user, Stage parentStage) {
        super(user, parentStage);
        Button saveButton = new Button("Save");
        saveButton.setGraphic(new ImageView(new Image("disk-black.png")));
        saveButton.setOnAction((event) -> {
            user.setUsername(usernameTxt.getText());
            if (passwordField.getText().equals(passwordFieldConf.getText()) && passwordField.getText().length() >= 5) {
                user.setPassword(DatabaseHandler.get_SHA_1_SecurePassword(passwordField.getText()));
            }

            user.setAdmin(adminCheckbox.selectedProperty().get());
            dialog.hide();
        });
        grid.add(saveButton, 1, 5);
    }
}
