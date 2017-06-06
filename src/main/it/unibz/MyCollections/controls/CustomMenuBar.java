package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuBar;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class CustomMenuBar extends MenuBar {

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    private FileMenu fileMenu;

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public FileMenu getFileMenu() {
        return fileMenu;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setFileMenu(FileMenu menu) {
        if (!this.getMenus().contains(menu)) {
            this.fileMenu = menu;
            this.getMenus().add(menu);
        }
    }
}
