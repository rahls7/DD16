package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Character;

/**
 * Created by Silas on 2017/2/10.
 */
public class CharacterEditorController{

    private Character characterModel;

    public CharacterEditorController(String name){
        characterModel = new Character(name);
    }

    public String getName(){return characterModel.getName();}

    public ArrayList getEquipment(){
        return characterModel.getEquipment();
    }

    public int[][] getStats(){ return characterModel.getStats();}

    public int[] getAttributes(){return characterModel.getAttributes();}
}