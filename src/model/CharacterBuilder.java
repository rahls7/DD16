package model;

/**
 * Created by mo on 2017-03-11.
 */
public abstract class CharacterBuilder {

    protected Character character;

    public Character getCharacter() {
        return character;
    }

    public void createCharacter(String id) {
        character = new Character(id);
    }

    abstract void initiate();
}
