package main.it.unibz.MyCollections;

/**
 * Created by claudio on 11/05/2017.
 */

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.views.AboutView;
import main.it.unibz.MyCollections.views.DataSummaryView;
import main.it.unibz.MyCollections.views.ManageUsersView;
import main.it.unibz.MyCollections.views.RecordsView;

public class Login extends Application {
    public Scene scene;
    public MenuItem importData;
    public MenuItem exportData;
    public Stage primaryStage;

    @Override
    public void start(Stage parentStage) {
        primaryStage = parentStage;
        scene = new Scene(new VBox(), 400, 350);
        primaryStage.setTitle("JavaFX Welcome");

        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");

        MenuItem exit = new MenuItem("Exit");
        exit.setGraphic(new ImageView(new Image("cross-button.png")));
        exit.setOnAction((ActionEvent t) -> {
            //TODO: save
            try {
                DatabaseHandler.getInstance().c.commit();
            } catch (Exception ex) { //TODO: logger
            }

            Platform.exit();
            System.exit(0);
        });

        MenuItem about = new MenuItem("About");
        about.setOnAction((event -> new AboutView(primaryStage)));
        about.setGraphic(new ImageView(new Image("information-button.png")));

        importData = new MenuItem("Import");
        importData.setGraphic(new ImageView(new Image("card-import.png")));

        exportData = new MenuItem("Export");

        exportData.setGraphic(new ImageView(new Image("card-export.png")));

        MenuItem summaryData = new MenuItem("Summary of data");
        summaryData.setOnAction((event -> new DataSummaryView(primaryStage)));
        summaryData.setGraphic(new ImageView(new Image("dashboard.png")));

        MenuItem manageUsers = new MenuItem("Manage Users");
        manageUsers.setOnAction((event -> new ManageUsersView(primaryStage)));
        manageUsers.setGraphic(new ImageView(new Image("user.png")));

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        menuFile.getItems().addAll(about, importData, exportData, summaryData, manageUsers, separatorMenuItem, exit);

        menuBar.getMenus().addAll(menuFile);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

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
            if (pwBox.getText().length() < 5) {
                btn.setDisable(true);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Password cannot be less than 5 characters");
            } else {
                btn.setDisable(false);
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("");
            }
        });
        RecordsView view = new RecordsView();

        Pane box = view.box(this);
        Scene scene1 = new Scene(box, 600, 500);

        btn.setOnAction((event) -> {
            User user = null;

            try {
                user = DatabaseHandler.getInstance().getUser(userTextField.getText(), pwBox.getText());
            } catch (Exception ex) {  //TODO: use custom exception
                //ex.printStackTrace();
                actiontarget.setFill(Color.FIREBRICK); //TODO: logger
                actiontarget.setText("Wrong username or password");
                return;
            }

            if (user != null) {
                ((VBox) scene.getRoot()).getChildren().removeAll(grid);
                ((VBox) scene.getRoot()).setMinHeight(500);
                ((VBox) scene.getRoot()).setMinWidth(600);
                primaryStage.sizeToScene();
                // primaryStage.setScene();
                //((VBox) scene.getRoot()).getChildren().clear();
                ((VBox) scene.getRoot()).getChildren().add(box);
                //scene.setRoot(box);
                //primaryStage.setScene(scene1);

            }
        });


        //Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
