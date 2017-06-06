package main.it.unibz.MyCollections.controls;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class FileMenu extends Menu {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private ImportDataMenuItem importDataMenuItem;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private ExportDataMenuItem exportDataMenuItem;
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public FileMenu() {
        super("File");
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void addItem(AdvancedCustomMenuItem item) {
        if (item instanceof ImportDataMenuItem) this.importDataMenuItem = (ImportDataMenuItem) item;
        if (item instanceof ExportDataMenuItem) this.exportDataMenuItem = (ExportDataMenuItem) item;
        this.getItems().add((MenuItem) item);
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setAdminVisible(boolean visible) {
        ObservableList<MenuItem> items = this.getItems();
        for (MenuItem item : items) {
            AdvancedCustomMenuItem customMenuItem = (AdvancedCustomMenuItem) item;
            if (customMenuItem.isAdminOnly())
                customMenuItem.setVisibility(visible);
        }
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setDataVisible(boolean visible) {
        ObservableList<MenuItem> items = this.getItems();
        for (MenuItem item : items) {
            AdvancedCustomMenuItem customMenuItem = (AdvancedCustomMenuItem) item;
            if (customMenuItem.isDataOnly())
                customMenuItem.setVisibility(visible);
        }
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ImportDataMenuItem getImportDataMenuItem() {
        return this.importDataMenuItem;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ExportDataMenuItem getExportDataMenuItem() {
        return this.exportDataMenuItem;
    }

}
