package it.unibz.MyCollections.controls;

import javafx.scene.control.MenuBar;

/**
 * A JavaFX {@link MenuBar} with added functionality to contain a special
 * menu called the file menu. The file menu is the primary and only
 * menu of the application.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class CustomMenuBar extends MenuBar {

    /**
     * Holds the FileMenu of this {@link CustomMenuBar}. This allows the application to edit the menu
     * later down during execution.
     *
     * @see FileMenu
     */
    private FileMenu fileMenu;

    /**
     * Gets the FileMenu of this class. This gives access to the private field above.
     *
     * @return The FileMenu instance.
     */
    public FileMenu getFileMenu() {
        return fileMenu;
    }

    /**
     * Sets the {@link FileMenu} of this class if the menu doesn't already contain
     * the FileMenu and adds it to the control.
     *
     * @param menu The FileMenu to set.
     */
    public void setFileMenu(FileMenu menu) {
        if (!this.getMenus().contains(menu)) {
            this.fileMenu = menu;
            this.getMenus().add(menu);
        }
    }
}
