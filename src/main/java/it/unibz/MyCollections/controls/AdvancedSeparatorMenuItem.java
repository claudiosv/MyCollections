package it.unibz.MyCollections.controls;

import javafx.scene.control.SeparatorMenuItem;

/**
 * A simple implementation of a menu separator by implementing the <code>AdvancedCustomMenuItem</code>
 * and extending JavaFX built in <code>SeparatorMenuItem</code>.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public class AdvancedSeparatorMenuItem extends SeparatorMenuItem implements AdvancedCustomMenuItem {

    /**
     * Gets whether this {@link AdvancedSeparatorMenuItem} is restricted only to admin users.
     * This ensures the menu item will be visible to non-admin users too.
     *
     * @return <code>false</code> to represent that the separator is visible to everyone.
     */
    @Override
    public boolean isAdminOnly() {
        return false;
    }

    /**
     * Gets whether the menu item is dependant on data being loaded.
     * This ensures the menu item will be visible to non-logged in users.
     *
     * @return <code>false</code> to represent that <code>{@link AdvancedSeparatorMenuItem}</code> is visible at all times.
     */
    @Override
    public boolean isDataOnly() {
        return false;
    }

    /**
     * Sets the control's visibility. This allows the item to be hidden or shown as needed.
     *
     * @param visible Whether the separator should be visible or not.
     */
    @Override
    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }
}
