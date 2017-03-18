package model;

/**
 * Created by mo on 2017-03-11.
 */
public class Player {
    private CharacterBuilder characterBuilder;

    public void setCharacterBuilder(CharacterBuilder newCharacterBuilder) {
        characterBuilder = newCharacterBuilder;
    }

    public void constructCharacter(String id) {
        characterBuilder.createCharacter(id);
        characterBuilder.initiate();
    }

    public Character getCharacter() {
        return characterBuilder.getCharacter();
    }
}
