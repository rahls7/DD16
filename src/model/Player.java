package model;

/**
 * Created by mo on 2017-03-11.
 */
public class Player {
    private CharacterBuilder characterBuilder;

    /**
     * set character builder
     * @param newCharacterBuilder
     */
    public void setCharacterBuilder(CharacterBuilder newCharacterBuilder) {
        characterBuilder = newCharacterBuilder;
    }

    /**
     * build the character
     * @param id
     */
    public void constructCharacter(String id) {
        characterBuilder.createCharacter(id);
        characterBuilder.initiate();
    }

    /**
     * get character that is built
     * @return
     */
    public Character getCharacter() {
        return characterBuilder.getCharacter();
    }
}
