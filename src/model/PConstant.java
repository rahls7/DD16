package model;


/**
 * Subclass of PCellContent. Represent the object of the constant content on cells including wall, entry and exit.
 *
 * @author Jiayao
 * @version 1.0.0
 */
public class PConstant extends PCellContent{

    /**
     * Constructor to initiate the Object.
     *
     * @param type The type of the item.
     */
    public PConstant(String type) {
        this.type = type;
    }
}
