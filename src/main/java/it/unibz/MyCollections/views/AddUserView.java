package it.unibz.MyCollections.views;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import it.unibz.MyCollections.DatabaseSession;
import it.unibz.MyCollections.HasherFactory;
import it.unibz.MyCollections.User;
import it.unibz.MyCollections.Validator;
import it.unibz.MyCollections.exceptions.UserAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View to add users to userbase. This view extends the abstract
 * UserView which contains all the fields necessary to edit or add
 * a user. The purpose of this class is to add an "Add" button
 * and process all the logic necessary.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see it.unibz.MyCollections.views.UserView
 * @since 1.0
 */
class AddUserView extends UserView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(AddUserView.class.getName());

    /**
     * Instantiates the add user view by creating the necessary controls
     * and adding them to the parent stage.
     *
     * @param user1       Previously instantiated user to be added.
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    public AddUserView(User user1, Stage parentStage) {
        super(user1, parentStage);
        logger.entering(getClass().getName(), "AddUserView");
        dialog.setTitle("Add User");
        dialog.getIcons().add(new Image("user-plus.png"));
        Button saveButton = new Button("Add");
        saveButton.setGraphic(new ImageView(new Image("user-plus.png")));
        saveButton.setOnAction((event) -> {
            logger.log(Level.INFO, "Add button clicked");
            if (Validator.isValidUsername(usernameTxt.getText())) {
                user.setUsername(usernameTxt.getText());
                if (passwordField.getText().equals(passwordFieldConf.getText()) && Validator.isValidPassword(passwordField.getText())) {
                    HasherFactory hasherFactory = new HasherFactory();
                    user.setPassword(hasherFactory.getHasher("sha512").hash(passwordField.getText()));
                    user.setAdmin(adminCheckbox.selectedProperty().get());
                    try {
                        DatabaseSession.getInstance().addUser(user);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "SQL error loading records", ex);
                    } catch (UserAlreadyExistsException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error adding user");
                        alert.setContentText("A user with the name " + usernameTxt.getText() + " already exists.");
                        alert.showAndWait();
                        logger.log(Level.SEVERE, "User already exists", ex);
                    }
                }
            }
            dialog.hide();
        });
        grid.add(saveButton, 1, 5);
        logger.exiting(getClass().getName(), "AddUserView");
    }
}
