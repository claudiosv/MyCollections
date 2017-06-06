package main.it.unibz.MyCollections.controls;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
interface AdvancedCustomMenuItem {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    boolean isAdminOnly();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    boolean isDataOnly();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    void setVisibility(boolean visible);
}
