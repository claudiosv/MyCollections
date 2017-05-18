package main.it.unibz.MyCollections;

/**
 * Created by claudio on 11/05/2017.
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

    public class Login extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("JavaFX Welcome");
            Scene scene = new Scene(new VBox(), 400, 350);

            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("File");

            ImageView img = new ImageView(new Image(this.getClass().getResource("plus-circle.png").toString()));
            img.setFitHeight(16);
            img.setFitWidth(16);

            MenuItem add = new MenuItem("Exit", img);
            add.setOnAction((ActionEvent t) -> {
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


            final Label label = new Label("Address Book");
            label.setFont(new Font("Arial", 20));
            TableView table = new TableView();
            table.setEditable(true);

            TableColumn firstNameCol = new TableColumn("First Name");
            TableColumn lastNameCol = new TableColumn("Last Name");
            TableColumn emailCol = new TableColumn("Email");

            table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table);

            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Sign in button pressed");
                    ((VBox) scene.getRoot()).getChildren().clear();
                    ((VBox) scene.getRoot()).getChildren().addAll(vbox);
                }
            });
            ((VBox) scene.getRoot()).getChildren().addAll(grid);


            //Scene scene = new Scene(grid, 300, 275);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
