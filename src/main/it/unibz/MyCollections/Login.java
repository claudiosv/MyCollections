package main.it.unibz.MyCollections;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.exceptions.UserNotFoundException;
import main.it.unibz.MyCollections.views.AboutView;
import main.it.unibz.MyCollections.views.DataSummaryView;
import main.it.unibz.MyCollections.views.ManageUsersView;
import main.it.unibz.MyCollections.views.RecordsView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Main application class/login view.
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class Login extends Application {
    private static final Logger logger = Logger.getLogger(Login.class.getName());
    public Scene scene;
    public MenuItem importData;
    public MenuItem exportData;
    public Stage primaryStage;
    public MenuItem summaryData;
    public MenuItem manageUsers;
    public Menu menuFile;


    public void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage parentStage) {
        logger.entering(getClass().getName(), "start");

        primaryStage = parentStage;
        scene = new Scene(new VBox(), 400, 350);

        primaryStage.setTitle("MyCollections Login");

        MenuBar menuBar = new MenuBar();

        menuFile = new Menu("File");

        MenuItem exit = new MenuItem("Exit");
        exit.setGraphic(new ImageView(new Image("cross-button.png")));
        exit.setOnAction((ActionEvent t) -> {
            logger.log(Level.INFO,"Saving & Exiting");
            DatabaseSession.getInstance().save();
            Platform.exit();
            System.exit(0);
        });

        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> {
            logger.log(Level.INFO,"Opening about view");
            new AboutView(primaryStage);
        });
        about.setGraphic(new ImageView(new Image("information-button.png")));

        importData = new MenuItem("Import");
        importData.setGraphic(new ImageView(new Image("card-import.png")));

        exportData = new MenuItem("Export");
        exportData.setGraphic(new ImageView(new Image("card-export.png")));

        summaryData = new MenuItem("Summary of data");
        summaryData.setOnAction((event -> new DataSummaryView(primaryStage)));
        summaryData.setGraphic(new ImageView(new Image("dashboard.png")));

        manageUsers = new MenuItem("Manage Users");
        manageUsers.setOnAction((event -> new ManageUsersView(primaryStage)));
        manageUsers.setGraphic(new ImageView(new Image("user.png")));

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        menuFile.getItems().addAll(about, separatorMenuItem, exit);

        menuBar.getMenus().addAll(menuFile);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        logger.log(Level.FINEST,"Built menu & added to vbox");

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
                logger.log(Level.FINE,"Enter key pressed");
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
                logger.log(Level.FINEST,"Password valid");
                btn.setDisable(false);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("");
            } else {
                logger.log(Level.FINEST,"Password invalid");
                btn.setDisable(true);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Password cannot be less than 5 characters");
            }
        });


        btn.setOnAction((event) -> {
            logger.log(Level.INFO,"Logging in");
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
                Session.setActiveUser(user);
                logger.log(Level.INFO,"Session created");
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
}
