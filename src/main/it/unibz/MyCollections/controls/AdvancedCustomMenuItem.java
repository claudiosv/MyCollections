package main.it.unibz.MyCollections.controls;

/**
 * Interface to allow menu items to have visibility metadata that can be
 * referenced runtime. This interface is meant to be used with <code>MenuItem</code>
 * to allow some items to be hidden e.g. admin only menus.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
interface AdvancedCustomMenuItem {

    /**
     * Gets whether the menu item is restricted only to admin users.
     * This ensures the menu item will be hidden to non-admin users.
     *
     * @return <code>false</code> to represent that <code>AdvancedCustomMenuItem</code> is visible only to admins.
     */
    boolean isAdminOnly();

    /**
     * Gets whether the menu item is dependant on data being loaded.
     * This ensures the menu item will be hidden to non-logged in users.
     *
     * @return <code>false</code> to represent that <code>AdvancedCustomMenuItem</code> is visible at all times.
     */
    boolean isDataOnly();

    /**
     * Sets the control's visibility. This allows the item to be hidden or shown as needed.
     *
     * @param visible Whether the control should be visible or not.
     */
    void setVisibility(boolean visible);
}
