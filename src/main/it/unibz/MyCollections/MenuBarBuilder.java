package main.it.unibz.MyCollections;

import javafx.stage.Stage;
import main.it.unibz.MyCollections.controls.*;

/**
 * Builds MenuBars. This class builds MenuBars for use in the main application bits.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
class MenuBarBuilder {
    /**
     * Prepares a MainMenu object with the required MenuItems.
     *
     * @param primaryStage The primary Stage of the JavaFX application.
     * @return The prepared CustomMenuBar containing the menu items.
     */
    public CustomMenuBar prepareMainMenu(Stage primaryStage) {
        CustomMenuBar mainMenuBar = new CustomMenuBar();
        FileMenu fileMenu = new FileMenu();
        fileMenu.addItem(new AboutMenuItem(primaryStage));
        fileMenu.addItem(new ExportDataMenuItem(primaryStage));
        fileMenu.addItem(new ImportDataMenuItem(primaryStage));
        fileMenu.addItem(new DataSummaryMenuItem(primaryStage));
        fileMenu.addItem(new ManageUsersMenuItem(primaryStage));
        fileMenu.addItem(new AdvancedSeparatorMenuItem());
        fileMenu.addItem(new ExitMenuItem());
        mainMenuBar.setFileMenu(fileMenu);
        return mainMenuBar;
    }
}
