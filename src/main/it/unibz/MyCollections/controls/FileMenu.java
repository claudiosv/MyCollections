package main.it.unibz.MyCollections.controls;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import main.it.unibz.MyCollections.Exporter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Claudio on 06/06/2017.
 */
public class FileMenu extends Menu {
    public FileMenu() {
        super("File");
    }
    private ImportDataMenuItem importDataMenuItem;
    private ExportDataMenuItem exportDataMenuItem;

    public void addItem(AdvancedCustomMenuItem item){
        if(item instanceof ImportDataMenuItem) this.importDataMenuItem = (ImportDataMenuItem)item;
        if(item instanceof ExportDataMenuItem) this.exportDataMenuItem = (ExportDataMenuItem)item;
        this.getItems().add((MenuItem)item);
    }

    public void setAdminVisibility(boolean visible)
    {
        ObservableList<MenuItem> items = this.getItems();
        for(MenuItem item : items)
        {
            AdvancedCustomMenuItem customMenuItem = (AdvancedCustomMenuItem) item;
            if(customMenuItem.isAdminOnly())
                customMenuItem.setVisibility(visible);
        }
    }

    public void setDataVisibility(boolean visible)
    {
        ObservableList<MenuItem> items = this.getItems();
        for(MenuItem item : items)
        {
            AdvancedCustomMenuItem customMenuItem = (AdvancedCustomMenuItem) item;
            if(customMenuItem.isDataOnly())
                customMenuItem.setVisibility(visible);
        }
    }

    public ImportDataMenuItem getImportDataMenuItem()
    {
        return this.importDataMenuItem;
    }

    public ExportDataMenuItem getExportDataMenuItem()
    {
        return this.exportDataMenuItem;
    }

}
