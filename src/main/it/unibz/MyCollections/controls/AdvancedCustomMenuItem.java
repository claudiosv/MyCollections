package main.it.unibz.MyCollections.controls;

/**
 * Factory to create controls.
 *
 * @author Claudio Spiess
 * @version 1.0
 * @since 1.0
 */
public interface AdvancedCustomMenuItem  {
    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public boolean isAdminOnly();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public boolean isDataOnly();

    /**
     * Factory to create controls.
     *
     * @author Claudio Spiess
     * @version 1.0
     * @since 1.0
     */
    public void setVisibility(boolean visible);
}
