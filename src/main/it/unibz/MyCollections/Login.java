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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {
        Scene scene;
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            //new RecordView();

            scene = new Scene(new VBox(), 400, 350);
            primaryStage.setTitle("JavaFX Welcome");

            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("File");

            //ImageView img = new ImageView(new Image(this.getClass().getResource("plus-circle.png").toString()));
            //img.setFitHeight(16);
            //img.setFitWidth(16);

            MenuItem add = new MenuItem("Exit");
            add.setOnAction((ActionEvent t) -> {
                //TODO: save
                Platform.exit();
                System.exit(0);
            });

            MenuItem about = new MenuItem("About");

            menuFile.getItems().add(about);

            menuFile.getItems().addAll(add);

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
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            Button btn = new Button("Sign in");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);

            final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);
            ((VBox) scene.getRoot()).getChildren().addAll(grid);
            RecordsView view = new RecordsView();

            VBox box = view.box();
            Scene scene1 = new Scene(box, 600, 500);

            btn.setOnAction((event) -> {

                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Sign in button pressed");
                primaryStage.setScene(scene1);
                    //((VBox) scene.getRoot()).getChildren().clear();
                    //((VBox) scene.getRoot()).getChildren().addAll(box);

            });



            //Scene scene = new Scene(grid, 300, 275);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
