package main.it.unibz.MyCollections.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.HasherFactory;
import main.it.unibz.MyCollections.SQLiteHandler;
import main.it.unibz.MyCollections.User;
import main.it.unibz.MyCollections.Validator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 31/05/2017.
 */
public class EditUserView extends UserView {
    private static final Logger logger = Logger.getLogger(EditUserView.class.getName());
    public EditUserView(User user, Stage parentStage) {
        super(user, parentStage);
        logger.entering(getClass().getName(), "EditUserView");
        Button saveButton = new Button("Save");
        saveButton.setGraphic(new ImageView(new Image("disk-black.png")));
        saveButton.setOnAction((event) -> {
            logger.log(Level.INFO, "Save button clicked");
            user.setUsername(usernameTxt.getText());
            if (passwordField.getText().equals(passwordFieldConf.getText()) && Validator.isValidPassword(passwordField.getText())) {
                HasherFactory hasherFactory = new HasherFactory();
                user.setPassword(hasherFactory.getHasher("sha512").hash(passwordField.getText()));
            }

            user.setAdmin(adminCheckbox.selectedProperty().get());
            dialog.hide();
        });
        grid.add(saveButton, 1, 5);
        logger.exiting(getClass().getName(), "EditUserView");
    }
}
