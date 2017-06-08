package main.it.unibz.MyCollections.controls;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * This class implements a custom (extended) menu, allowing the menu to have easily accessible properties.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class FileMenu extends Menu {
    /**
     * Holds this FileMenu's Import Data Menu Item. This is necessary so it can be
     * easily accessed once its action needs to be set.
     *
     * @see MenuItem
     */
    private ImportDataMenuItem importDataMenuItem;

    /**
     * Holds this FileMenu's Export Data Menu Item. This is necessary so it can be
     * easily accessed once its action needs to be set.
     *
     * @see MenuItem
     */
    private ExportDataMenuItem exportDataMenuItem;

    /**
     * Constructs a FileMenu by calling the super constructor with the appropriate text.
     */
    public FileMenu() {
        super("File");
    }

    /**
     * Adds an item to this FileMenu. If the item being added is a special item (export/import),
     * the appropriate parameter is set.
     *
     * @param item The AdvancedCustomMenuItem to add to the FileMenu.
     */
    public void addItem(AdvancedCustomMenuItem item) {
        if (item instanceof ImportDataMenuItem) this.importDataMenuItem = (ImportDataMenuItem) item;
        if (item instanceof ExportDataMenuItem) this.exportDataMenuItem = (ExportDataMenuItem) item;
        this.getItems().add((MenuItem) item);
    }

    /**
     * Sets the control's visibility for admin-only items. This allows admin-only items to be shown
     * or hidden as needed.
     *
     * @param visible Whether the admin controls should be visible or not.
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
     * Sets the control's visibility for data-only items. This allows data-only items to be shown
     * or hidden as needed.
     *
     * @param visible Whether the data controls should be visible or not.
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
     * Gets the {@link ImportDataMenuItem}.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ImportDataMenuItem getImportDataMenuItem() {
        return this.importDataMenuItem;
    }

    /**
     * Gets the {@link ExportDataMenuItem}.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public ExportDataMenuItem getExportDataMenuItem() {
        return this.exportDataMenuItem;
    }

}
