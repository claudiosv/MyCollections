package main.it.unibz.MyCollections.views;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.DatabaseHandler;
import main.it.unibz.MyCollections.User;

/**
 * Created by Claudio on 02/06/2017.
 */
public class AddUserView extends UserView {
    public AddUserView(User user1, Stage parentStage) {
        super(user1, parentStage);
        Button saveButton = new Button("Add");
        //saveButton.setGraphic(new ImageView(new Image("disk-black.png")));
        saveButton.setOnAction((event) -> {
            user.setUsername(usernameTxt.getText());
            if (passwordField.getText().equals(passwordFieldConf.getText()) && passwordField.getText().length() >= 5) {
                user.setPassword(DatabaseHandler.get_SHA_1_SecurePassword(passwordField.getText()));
            }

            user.setAdmin(adminCheckbox.selectedProperty().get());
            try {
                DatabaseHandler.getInstance().addUser(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dialog.hide();
        });
        grid.add(saveButton, 1, 5);
    }
}
