package model;

import java.util.Observable;

/**
 * The super class of the different contents on cells which can be observed.
 *
 * @author Jiayao Zhou
 * @version 1.0.1
 */
public abstract class PCellContent extends Observable {
    protected String type;


    /**
     * Return the type of the content.
     *
     * @return The type of the content.
     */
    public String getType() {
        return type;
    }
}
