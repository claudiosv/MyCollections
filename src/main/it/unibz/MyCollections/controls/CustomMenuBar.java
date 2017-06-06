package main.it.unibz.MyCollections.controls;

import javafx.scene.control.MenuBar;

/**
 * Created by Claudio on 06/06/2017.
 */
public class CustomMenuBar extends MenuBar {

    private FileMenu fileMenu;

    public void setFileMenu(FileMenu menu)
    {
        if(!this.getMenus().contains(menu)) {
            this.fileMenu = menu;
            this.getMenus().add(menu);
        }
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }
}
