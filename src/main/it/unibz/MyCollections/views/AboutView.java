package main.it.unibz.MyCollections.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View that creates a dialog which shows information about project &amp; author.
 * This fulfills the requirement
 * "About: pops up a dialogue that tells something about the developer of the application
 * (you)."
 * The purpose of this class is to create the necessary controls and display the dialog.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class AboutView {
    private static final Logger logger = Logger.getLogger(AboutView.class.getName());

    /**
     * Instantiates an AboutView which shows information about project &amp; author.
     * This fulfills the requirement
     * "About: pops up a dialogue that tells something about the developer of the application
     * (you)."
     *
     * @param parentStage Stage from which constructor is called.
     * @author Claudio Spiess
     */
    public AboutView(Stage parentStage) {
        logger.entering(getClass().getName(), "AboutView");
        Stage dialog = new Stage();
        dialog.getIcons().add(new Image("information-button.png"));
        dialog.setTitle("About MyCollections");
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new VBox(), 600, 350);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        ImageView image = new ImageView(new Image("photo.jpg", 150, 200, true, true));
        grid.add(image, 0, 0, 1, 3);
        Text scenetitle = new Text("MyCollections 1.0 - About the author");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setTextAlignment(TextAlignment.JUSTIFY);
        grid.add(scenetitle, 1, 0);

        Text userName = new Text("Claudio Spiess is a first year computer science student at the Free University of Bolzano-Bozen. Claudio started programming in VB.NET and eventually began working in Java with the popular game \"Minecraft\", in 2011. He also has years of experience in PHP & C#. MyCollections is his exam project for the course \"Advanced Programming\" lectured by Prof. Marko Tkalčič. MyCollections is a JavaFX-based address book application using SQLite." +
                "\nDefault user/record icon ©  Anna Litviniuk released in public domain from https://dribbble.com/shots/1883214-Flatflow-Icons-Free-Sketch3-Resource" +
                "\nAll icons are © Yusuke Kamiyamane licensed under a Creative Commons Attribution 3.0 License from http://p.yusukekamiyamane.com/");
        userName.setWrappingWidth(350);
        grid.add(userName, 1, 1, 1, 1);

        Button btn = new Button("Close");
        btn.setGraphic(new ImageView(new Image("cross-button.png")));
        btn.setOnAction((event) -> {
            logger.log(Level.INFO, "Closing about view");
            dialog.hide();
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);

        grid.add(hbBtn, 1, 2, 1, 1);

        ((VBox) scene.getRoot()).getChildren().add(grid);

        dialog.setScene(scene);
        dialog.showAndWait();
        logger.exiting(getClass().getName(), "AboutView");
    }

}
