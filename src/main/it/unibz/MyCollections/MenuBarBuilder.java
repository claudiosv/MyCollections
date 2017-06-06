package main.it.unibz.MyCollections;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.it.unibz.MyCollections.controls.*;
import main.it.unibz.MyCollections.views.AboutView;
import main.it.unibz.MyCollections.views.DataSummaryView;
import main.it.unibz.MyCollections.views.ManageUsersView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Claudio on 06/06/2017.
 */
public class MenuBarBuilder {
    public CustomMenuBar prepareMainMenu(Stage primaryStage)
    {
        CustomMenuBar mainMenuBar = new CustomMenuBar();
        FileMenu fileMenu = new FileMenu();
        fileMenu.addItem(new AboutMenuItem(primaryStage));
        fileMenu.addItem(new ImportDataMenuItem(primaryStage));
        fileMenu.addItem(new ExportDataMenuItem(primaryStage));
        fileMenu.addItem(new DataSummaryMenuItem(primaryStage));
        fileMenu.addItem(new ManageUsersMenuItem(primaryStage));
        fileMenu.addItem(new AdvancedSeparatorMenuItem());
        fileMenu.addItem(new ExitMenuItem());
        mainMenuBar.setFileMenu(fileMenu);
        return mainMenuBar;
    }
}
