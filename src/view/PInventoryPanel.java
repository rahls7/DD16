package view;

import controller.CharacterEditorController;
import controller.PlayController;
import model.*;
import model.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mo on 2017-03-11.
 */
public class PInventoryPanel extends JPanel implements Observer {

    private PCharacter pCharacter;
    private PlayController playController;
    private CharacterEditorController characterEditorController;
    JButton showButton;
    JButton takeoffButton, putonButton;
    int flag = 0;
    JComboBox equipmentComboBox, backpackJComboBox;
    JLabel equipmentLabel, backpackLabel;

    /**
     * constructor of inventory panel
     * set all basic components
     */
    public PInventoryPanel() {
        super();
        setLayout(null);
        characterEditorController = new CharacterEditorController("123", "Bully");
        takeoffButton = new JButton("Take off");
        takeoffButton.setBounds(150, 40, 100, 30);
        putonButton = new JButton("Put on");
        putonButton.setBounds(490, 40, 100, 30);
        showButton = new JButton("SHOW");
        showButton.setBounds(10, 10, 80, 20);
        equipmentLabel = new JLabel("Equipment");
        equipmentLabel.setBounds(10, 30, 250, 50);
        equipmentComboBox = new JComboBox();
        equipmentComboBox.setBounds(10, 60, 250, 50);
        backpackLabel = new JLabel("Backpack");
        backpackLabel.setBounds(350, 30, 250, 50);
        backpackJComboBox = new JComboBox();
        backpackJComboBox.setBounds(350, 60, 250, 50);
        Handler handler = new Handler();
        showButton.addActionListener(handler);
        takeoffButton.addActionListener(handler);
        putonButton.addActionListener(handler);
    }

    @Override
    /**
     * override the update function of observer
     * when the character changed, it will show the character again
     */
    public void update(Observable o, Object arg) {
        removeAll();
        pCharacter = (PCharacter) o;
        characterEditorController.setpCharacter(pCharacter);
        equipmentComboBox.setModel(new DefaultComboBoxModel(itemToString(pCharacter.getEquipment()).toArray()));
        backpackJComboBox.setModel(new DefaultComboBoxModel(itemToString(pCharacter.getBackpack()).toArray()));
        if (flag==0) {
            add(showButton);
        }else
        {
            add(showButton);
            add(equipmentLabel);
            add(equipmentComboBox);
            add(backpackLabel);
            add(backpackJComboBox);
            if (pCharacter.getCategory()==2)
            {
                add(takeoffButton);
                add(putonButton);
            }
        }
        validate();
        repaint();
    }

    /**
     * transform the items to be strings
     * @param items
     * @return
     */
    public ArrayList<String> itemToString(ArrayList<model.PItem> items) {
        ArrayList<String> strings = new ArrayList<String>();
        String s;
        for (model.PItem item : items) {
            s = item.getType() + ":" + item.getAttribute() + "+" + item.getAttributeValue();
            strings.add(s);
        }
        return strings;
    }

    /**
     * clean this panel up
     */
    public void clean() {
        removeAll();
        flag = 0;
        validate();
        repaint();
    }

    /**
     * set the play controller
     * @param playController
     */
    public void setPlayController(PlayController playController){
        this.playController = playController;
    }

    /**
     * action listener,
     * do different tasks when different buttons click
     */
    class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == showButton) {
                if (flag == 0) {
                    add(equipmentLabel);
                    add(equipmentComboBox);
                    add(backpackLabel);
                    add(backpackJComboBox);
                    if (pCharacter.getCategory()==2)
                    {
                        add(takeoffButton);
                        add(putonButton);
                    }
                    flag = 1;
                    validate();
                    repaint();
                } else {
                    removeAll();
                    add(showButton);
                    flag = 0;
                    validate();
                    repaint();
                }
            } else if (event.getSource() == takeoffButton){
                int i = equipmentComboBox.getSelectedIndex();
                ArrayList<PItem> equipment = playController.getPlayer().getEquipment();
                PItem pItem = equipment.get(i);
                equipment.remove(i);
                playController.setEquipment(equipment);
                ArrayList<PItem> backpack = playController.getPlayer().getBackpack();
                backpack.add(pItem);
                playController.setBackpack(backpack);
                playController.recalculateStats();
                playController.inventoryView();
                playController.characterView();
            } else if (event.getSource() == putonButton){
                int i = backpackJComboBox.getSelectedIndex();
                ArrayList<PItem> backpack = playController.getPlayer().getBackpack();
                ArrayList<PItem> equipment = playController.getPlayer().getEquipment();
                PItem pItem = backpack.get(i);
                for (PItem item : equipment) {
                    if (item.getType().equals(pItem.getType())) {
                        equipment.remove(item);
                        backpack.add(item);
                        break;
                    }
                }
                backpack.remove(pItem);
                playController.setBackpack(backpack);
                equipment.add(pItem);
                playController.setEquipment(equipment);
                playController.recalculateStats();
                playController.inventoryView();
                playController.characterView();
            }
        }
    }

}
