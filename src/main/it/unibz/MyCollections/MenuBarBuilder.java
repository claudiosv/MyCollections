package main.it.unibz.MyCollections;

import javafx.stage.Stage;
import main.it.unibz.MyCollections.controls.*;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
class MenuBarBuilder {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public CustomMenuBar prepareMainMenu(Stage primaryStage) {
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
