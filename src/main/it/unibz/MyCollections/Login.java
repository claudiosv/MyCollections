package main.it.unibz.MyCollections;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.controls.CustomMenuBar;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;
import main.it.unibz.MyCollections.views.RecordsView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.*;

/**
 * Main class to implement the task.
 * This is the ultimate entry point of the application.
 * Initialises database and starts JavaFX application.
 * This is the core class of the application. It contains
 * the main stage, the login view, the menus, and calls
 * the next views upon successful login.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Login extends Application {
    private static final Logger logger = Logger.getLogger("main.it.unibz.MyCollections");
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private Scene scene;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public Stage primaryStage;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private CustomMenuBar menuBar;

    /**
     * Main method. Entry point of application.
     * Initialises database and starts JavaFX application.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Populates main stage with controls and displays login form.
     * This method creates menus, and the view/controls to login.
     *
     * @param parentStage Parent stage from JavaFX
     * @author Claudio Spiess
     */
    @Override
    public void start(Stage parentStage) {
        logger.setLevel(Level.ALL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINER);
        logger.addHandler(consoleHandler);

        try {
            FileHandler handler = new FileHandler("MyCollections-log.%u.%g.txt", 1024 * 1024 * 8, 10, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Log file failed to open", ex);
        }

        logger.log(Level.INFO, "Logging started, opening database");
        DatabaseSession.getInstance().initialise("user_records.db");

        //TODO: Add Edit,Add options to context menu
        //TODO: Correct titles, add icons
        //TODO: Add button to end search mode, add view for ActiveUser
        //TODO: Add check for username, passwords when adding users
        //TODO: Check users for permissions when working with stuff
        //TODO: refactor entry point a bit to be more logical
        //TODO: fix double writing to console
        //TODO: fix bug that opening a record erases the picture
        //TODO: fix users list


        this.primaryStage = parentStage;
        scene = new Scene(new VBox(), 400, 350);

        this.primaryStage.setTitle("MyCollections Login");

        MenuBarBuilder builder = new MenuBarBuilder();
        menuBar = builder.prepareMainMenu(primaryStage);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        logger.log(Level.FINEST, "Built menu & added to vbox");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setText("admin");
        //userTextField.setPromptText("admin");
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();

        pwBox.setText("admin");
        //pwBox.setPromptText("admin");
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        //TODO: btn.setDisable(true);
        pwBox.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                logger.log(Level.FINE, "Enter key pressed");
                btn.fire();
            }
        });

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        ((VBox) scene.getRoot()).getChildren().addAll(grid);
        pwBox.textProperty().addListener((obs, oldText, newText) -> {
            if (Validator.isValidPassword(pwBox.getText())) {
                logger.log(Level.FINEST, "Password valid");
                btn.setDisable(false);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("");
            } else {
                logger.log(Level.FINEST, "Password invalid");
                btn.setDisable(true);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Password cannot be less than 5 characters");
            }
        });


        btn.setOnAction((event) -> {
            logger.log(Level.INFO, "Logging in");
            User user = null;
            try {
                HasherFactory hasherFactory = new HasherFactory();
                user = DatabaseSession.getInstance().getUser(userTextField.getText(), hasherFactory.getHasher("sha512").hash(pwBox.getText()));
            } catch (UserNotFoundException ex) {
                logger.log(Level.WARNING, "Login failed", ex);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Wrong username or password");
                return;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "SQL error", ex);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "IO error", ex);
            }

            if (user != null) {
                Session.getInstance().setActiveUser(user);
                logger.log(Level.INFO, "Session created");
                menuBar.getFileMenu().setAdminVisible();
                menuBar.getFileMenu().setDataVisible();
                RecordsView view = new RecordsView();
                Pane box = view.box(this);
                ((VBox) scene.getRoot()).getChildren().remove(grid);
                primaryStage.setHeight(600);
                primaryStage.setWidth(800);
                VBox.setVgrow(box, Priority.ALWAYS);
                ((VBox) scene.getRoot()).getChildren().add(box);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        logger.exiting(getClass().getName(), "start");
    }

    public CustomMenuBar getMenuBar() {
        return menuBar;
    }
}
