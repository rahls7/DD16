package build1.character;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Silas on 2017/2/10.
 */
public class CharacterController{

    private CharacterModel characterModel;

    CharacterController(){
        characterModel = new CharacterModel();
    }

    public ArrayList getEquipment(){
        return characterModel.getEquipment();
    }
}
