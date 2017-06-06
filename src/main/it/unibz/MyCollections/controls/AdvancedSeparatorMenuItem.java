package main.it.unibz.MyCollections.controls;

import javafx.scene.control.SeparatorMenuItem;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class AdvancedSeparatorMenuItem extends SeparatorMenuItem implements AdvancedCustomMenuItem {

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public boolean isDataOnly() {
        return false;
    }

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void setVisible() {
        this.setVisible(true);
    }
}
