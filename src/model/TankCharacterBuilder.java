package model;

/**
 * Created by mo on 2017-03-11.
 */
public class TankCharacterBuilder extends CharacterBuilder {
    /**
     * initiate the type of character
     */
    public void initiate() {
        character.initiateStats("Tank");
    }
}