package model;

import java.util.Observable;

public abstract class PCellContent extends Observable {
    protected String type;

    public String getType() { return type; }
}
