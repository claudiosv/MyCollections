package it.unibz.MyCollections.views;

import javafx.embed.swing.SwingFXUtils;
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
import it.unibz.MyCollections.User;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract view to manage a user. The purpose of this class
 * is to build the editable fields necessary to edit
 * or add a user to the userbase.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @see User
 * @see EditUserView
 * @see AddUserView
 * @since 1.0
 */
abstract class UserView {
    /**
     * Holds logger for this class.
     */
    private static final Logger logger = Logger.getLogger(UserView.class.getName());

    /**
     * Holds the GridPane for the JavaFX Stage.
     */
    final GridPane grid;

    /**
     * Holds the JavaFX Stage.
     */
    final Stage dialog;

    /**
     * Holds the TextField to edit the username.
     */
    final TextField usernameTxt;

    /**
     * Holds the PasswordField to edit the password.
     */
    final PasswordField passwordField;

    /**
     * Holds the PasswordField to confirm that the password is typed correctly.
     */
    final PasswordField passwordFieldConf;

    /**
     * Holds the CheckBox to determine whether the user is an admin or not.
     */
    final CheckBox adminCheckbox;

    /**
     * Holds the {@link User} which is being edited.
     */
    final User user;

    /**
     * Instantiates view to manage user. Adds the controls
     * necessary to edit or add a user's properties to the
     * database.
     *
     * @param user        previously instantiated record to be added.
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    UserView(User user, Stage parentStage) {
        logger.entering(getClass().getName(), "UserView");
        this.user = user;
        dialog = new Stage();
        dialog.getIcons().add(new Image("user-pencil.png"));
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 300, 350);
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(128);
        imageView.setFitWidth(128);
        imageView.setImage(user.getImage());
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
            logger.log(Level.INFO, "Browsing for file");
            File file = fileChooser.showOpenDialog(dialog);
            if (file != null) {
                try {
                    user.setImage(SwingFXUtils.toFXImage(ImageIO.read(file), null));
                    imageView.setImage(user.getImage());
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Image error");
                    alert.setContentText("Image could not be successfully loaded from file: " + file.toString() + "\n\nPlease try a valid JPG or PNG.");
                    alert.showAndWait();
                    logger.log(Level.SEVERE, "IO error loading image", ex);
                }
            }
        });
        grid.add(openButton, 1, 0);


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
        logger.exiting(getClass().getName(), "UserView");
    }

    /**
     * Shows dialog to manage user. This allows the user to easily edit
     * the fields of a user. Returns the new or edited User instance.
     *
     * @return User that was created or edited.
     * @author Claudio Spiess
     */
    public User show() {
        logger.entering(getClass().getName(), "show");
        dialog.showAndWait();
        logger.exiting(getClass().getName(), "show");
        return user;
    }
}
