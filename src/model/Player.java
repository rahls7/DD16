package model;

/**
 * Player object.
 *
 * @author Jiayao
 * @version 1.0.0
 */
public class Player {
    private CharacterBuilder characterBuilder;

    /**
     * set character builder
     *
     * @param newCharacterBuilder
     */
    public void setCharacterBuilder(CharacterBuilder newCharacterBuilder) {
        characterBuilder = newCharacterBuilder;
    }

    /**
     * build the character
     *
     * @param id
     */
    public void constructCharacter(String id) {
        characterBuilder.createCharacter(id);
        characterBuilder.initiate();
    }

    /**
     * get character that is built
     * @return Character
     */
    public Character getCharacter() {
        return characterBuilder.getCharacter();
    }
}
