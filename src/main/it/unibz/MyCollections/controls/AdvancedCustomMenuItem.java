package main.it.unibz.MyCollections.controls;

import java.awt.*;

/**
 * Created by Claudio on 06/06/2017.
 */
public interface AdvancedCustomMenuItem  {

    public boolean isAdminOnly();

    public boolean isDataOnly();

    public void setVisibility(boolean visible);
}
