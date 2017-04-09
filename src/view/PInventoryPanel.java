package view;

import controller.CharacterEditorController;
import controller.PlayController;
import model.PCharacter;
import model.PItem;

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

    JButton showButton;
    JButton takeoffButton, putonButton;
    int flag = 0;
    JComboBox equipmentComboBox, backpackJComboBox;
    JLabel equipmentLabel, backpackLabel;
<<<<<<< HEAD
<<<<<<< Updated upstream
    private PCharacter pCharacter;
    private PlayController playController;
    private CharacterEditorController characterEditorController;
=======
    private PCellPanel[][] cells;
>>>>>>> Stashed changes
=======
    private PCellPanel[][] cells;
>>>>>>> d99dc5ba5e2e5d15e778e244e4f348d625824789

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
        if (flag == 0) {
            add(showButton);
        } else {
            add(showButton);
            add(equipmentLabel);
            add(equipmentComboBox);
            add(backpackLabel);
            add(backpackJComboBox);
            if (pCharacter.getCategory() == 2) {
                add(takeoffButton);
                add(putonButton);
            }
        }
        validate();
        repaint();
    }

    /**
     * transform the items to be strings
     *
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
     *
     * @param playController
     */
    public void setPlayController(PlayController playController) {
        this.playController = playController;
    }

    public void setCells(PCellPanel[][] cells) { this.cells = cells; }

    private void removeAttackRange() {
        for(int i = 0; i < cells.length; i++)
            for(int j = 0; j < cells[i].length; j++) {
                if(cells[i][j].isAttackRang == true) {
                    cells[i][j].removeAttackRange();
                }
            }
    }

    private void showAttackRange(int x, int y) {
        int[] ranged_x = {x-2, x, x, x+2, x-1, x, x, x+1, x-1, x+1, x-1, x+1};
        int[] ranged_y = {y, y-2, y+2, y, y, y-1, y+1, y, y-1, y+1, y+1, y-1};

        int[] melee_x = {x-1, x, x, x+1};
        int[] melee_y = {y, y-1, y+1, y};

        String weapon_type = playController.getWeaponType();
        if(weapon_type != null && weapon_type.equals("Ranged Weapon")) {
            for(int i = 0; i < ranged_x.length; i++){
                int cell_x = ranged_x[i];
                int cell_y = ranged_y[i];

                if(cell_x >= 0 && cell_y >= 0 && cell_x < cells.length && cell_y < cells[0].length){
                    cells[cell_x][cell_y].setAttackRange();
                }
            }
        }
        else {
            for(int i = 0; i < melee_x.length; i++){
                int cell_x = melee_x[i];
                int cell_y = melee_y[i];

                if(cell_x >= 0 && cell_y >= 0 && cell_x < cells.length && cell_y < cells[0].length){
                    cells[cell_x][cell_y].setAttackRange();
                }
            }
        }
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
                    if (pCharacter.getCategory() == 2) {
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
            } else if (event.getSource() == takeoffButton) {
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
<<<<<<< HEAD
<<<<<<< Updated upstream
            } else if (event.getSource() == putonButton) {
=======
=======
>>>>>>> d99dc5ba5e2e5d15e778e244e4f348d625824789
                if(pItem.getType().equals("Ranged Weapon")) {

                    removeAttackRange();
                    showAttackRange(playController.getPlayerX(), playController.getPlayerY());
                }
            } else if (event.getSource() == putonButton){
>>>>>>> Stashed changes
                int i = backpackJComboBox.getSelectedIndex();
                ArrayList<PItem> backpack = playController.getPlayer().getBackpack();
                ArrayList<PItem> equipment = playController.getPlayer().getEquipment();
                PItem pItem = backpack.get(i);
                for (PItem item : equipment) {
                    if (item.getType().equals(pItem.getType()) || (item.getType().equals("Ranged Weapon") && pItem.getType().equals("Melee Weapon")) || (item.getType().equals("Melee Weapon") && pItem.getType().equals("Ranged Weapon"))) {
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
                removeAttackRange();
                showAttackRange(playController.getPlayerX(), playController.getPlayerY());
            }
        }
    }

}
