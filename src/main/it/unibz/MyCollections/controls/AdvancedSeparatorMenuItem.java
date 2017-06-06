package main.it.unibz.MyCollections.controls;

import javafx.scene.control.SeparatorMenuItem;

/**
 * Created by Claudio on 06/06/2017.
 */
public class AdvancedSeparatorMenuItem extends SeparatorMenuItem implements AdvancedCustomMenuItem {
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    @Override
    public boolean isDataOnly() {
        return false;
    }

    @Override
    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }
}
