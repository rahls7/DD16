package model;

/**
 * Created by mo on 2017-03-11.
 */
public abstract class CharacterBuilder {

    protected Character character;

    /**
     * get the object of character from character builder
     *
     * @return character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * create character by this function
     *
     * @param id
     */
    public void createCharacter(String id) {
        character = new Character(id);
    }

    /**
     * initiate the type of character
     */
    abstract void initiate();
}
