package view;

import controller.CharacterEditorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Silas on 2017/2/10.
 */
public class CreateCharacter extends JPanel {

    public JButton createButton, confirmButton, takeoffButton, putonButton, deleteButton, pickButton;
    public JTextField nameTextField;
    public JTextField[] attributeTextField = new JTextField[6];
    public JTextField[][] statsTextField = new JTextField[6][2];
    String id;
    String name;
    int[] attribute = new int[6];
    int[][] stats = new int[6][2];
    ArrayList<String> items, backpack, equipment;
    JComboBox equipmentComboBox, backpackJComboBox, itemJComboBox;
    private CharacterEditorController characterController;

    /**
     * Constructor of createCharacter to create an object of CreateCharacter View
     *
     * @param id, ID of the character
     */
    public CreateCharacter(String id, String type) {

        //initiate
        super();
        setSize(2500, 600);
        setLayout(null);
        this.id = id;
        characterController = new CharacterEditorController(id, type);
        items = new ArrayList<String>();
        backpack = new ArrayList<String>();
        equipment = new ArrayList<String>();

        // Create AttributeLabels
        add(new AttributeLabel("name:", 0));
        add(new AttributeLabel("level:", 2));
        add(new AttributeLabel("Hit Point:", 3));
        add(new AttributeLabel("Armor Class:", 4));
        add(new AttributeLabel("Attack Bonus:", 5));
        add(new AttributeLabel("Damage Bonus:", 6));
        add(new AttributeLabel("Multiple Attacks:", 7));

        // Create StatsLabels
//        add(new StatsLabel("Ability:", 0));
        add(new StatsLabel("Strength:", 0));
        add(new StatsLabel("Dexterity:", 1));
        add(new StatsLabel("Constitution:", 2));
        add(new StatsLabel("Intelligence:", 3));
        add(new StatsLabel("Wisdom:", 4));
        add(new StatsLabel("Charisma:", 5));

        //Create Modifier Labels and the TextFiels of Stats
        for (int i = 0; i < 6; i++) {
            add(new StatsLabel("modifier:", i));
            for (int j = 0; j < 2; j++) {
                statsTextField[i][j] = new StatsTextField(i, j);
                add(statsTextField[i][j]);
            }
        }

        //Create AttributeTextFields
        nameTextField = new AttributeTextField(0);
        add(nameTextField);
        for (int i = 0; i < 6; i++) {
            attributeTextField[i] = new AttributeTextField(i + 2);
            add(attributeTextField[i]);
        }

        //Create buttons
        Handler handler = new Handler();
        createButton = new JButton("Create");
        createButton.setBounds(630, 370, 100, 50);
        add(createButton);
        createButton.addActionListener(handler);

        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(630, 450, 100, 50);
        add(confirmButton);
        confirmButton.addActionListener(handler);

        //backpack
        add(createItemJLabel("Equipment", 800, 50));
        add(createItemJLabel("Backpack", 1050, 50));
        add(createItemJLabel("Item", 1300, 50));
        takeoffButton = createItemJButton("Take off", 850, 300);
        putonButton = createItemJButton("Put on", 1100, 300);
        deleteButton = createItemJButton("Delete", 1100, 370);
        pickButton = createItemJButton("Pick", 1350, 300);
        add(takeoffButton);
        takeoffButton.addActionListener(handler);
        add(putonButton);
        putonButton.addActionListener(handler);
        add(deleteButton);
        deleteButton.addActionListener(handler);
        add(pickButton);
        pickButton.addActionListener(handler);

        equipmentComboBox = new JComboBox(equipment.toArray());
        equipmentComboBox.setBounds(800, 100, 250, 50);
        equipmentComboBox.setMaximumRowCount(7);
        add(equipmentComboBox);
        backpackJComboBox = new JComboBox(backpack.toArray());
        backpackJComboBox.setBounds(1050, 100, 250, 50);
        backpackJComboBox.setMaximumRowCount(7);
        add(backpackJComboBox);//{"Helmet:shit","Armor:bullshit","shield:wood"};
        items = characterController.getItem();
        itemJComboBox = new JComboBox(items.toArray());
        itemJComboBox.setBounds(1300, 100, 250, 50);
        itemJComboBox.setMaximumRowCount(7);
        add(itemJComboBox);
    }

    JLabel createItemJLabel(String name, int x, int y) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, 150, 50);
        label.setFont(new Font("dialog", 0, 20));
        return label;
    }

    JButton createItemJButton(String name, int x, int y) {
        JButton button = new JButton(name);
        button.setBounds(x, y, 100, 50);
        return button;
    }

    public void display() {
        stats = characterController.getStats();
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 2; j++)
                statsTextField[i][j].setText(Integer.toString(stats[i][j]));
        attribute = characterController.getAttributes();
        for (int i = 0; i < 6; i++)
            attributeTextField[i].setText(Integer.toString(attribute[i]));

        if (attribute[5] == 1)
            attributeTextField[5].setText("true");
        else
            attributeTextField[5].setText("False");


        equipmentComboBox.setModel(new DefaultComboBoxModel(characterController.getEquipment().toArray()));
        backpackJComboBox.setModel(new DefaultComboBoxModel(characterController.getBackpack().toArray()));
    }

    class Handler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == createButton) {
                String name = nameTextField.getText();
                characterController.setName(name);
//                characterController.initiateStats();
                remove(createButton);
                display();
                validate();
                repaint();
            } else if (event.getSource() == confirmButton) {
                characterController.saveCharacter();
                JOptionPane.showMessageDialog(confirmButton, "Success");

            } else if (event.getSource() == takeoffButton) {
                int i = equipmentComboBox.getSelectedIndex();
                characterController.setEquipmentBackpack(i);
                display();
                validate();
                repaint();
            } else if (event.getSource() == putonButton) {
                int i = backpackJComboBox.getSelectedIndex();
                characterController.setEquipment(i);
                characterController.removeBackpack(i);
                display();
                validate();
                repaint();
            } else if (event.getSource() == deleteButton) {
                int i = backpackJComboBox.getSelectedIndex();
                characterController.removeBackpack(i);
                display();
                validate();
                repaint();
            } else if (event.getSource() == pickButton) {
                int i = itemJComboBox.getSelectedIndex();
                characterController.setBackpack(i);
                display();
                validate();
                repaint();
            }
//
//                }
        }
    }

}

class StatsLabel extends JLabel {
    StatsLabel(String name, int number) {
        setText(name);
        if (name.equals("modifier:"))
            setBounds(560, number * 50 + 20, 120, 40);
        else
            setBounds(330, number * 50 + 20, 120, 40);
        setFont(new Font("dialog", 0, 15));
    }
}

class StatsTextField extends JTextField {
    StatsTextField(int i, int j) {
        setVisible(true);
        setFont(new Font("dialog", 0, 15));
        setLocation(430 + j * 200, i * 50 + 20);
        setSize(100, 40);
        setHorizontalAlignment(JTextField.CENTER);
    }
}

class AttributeLabel extends JLabel {
    AttributeLabel(String name, int number) {
        setText(name);
        setBounds(20, number * 50 + 20, 160, 40);
        setFont(new Font("dialog", 0, 15));
    }
}

class AttributeTextField extends JTextField {
    AttributeTextField(int i) {
        setVisible(true);
        setFont(new Font("dialog", 0, 15));
        setLocation(140, i * 50 + 20);
        setSize(100, 40);
        setHorizontalAlignment(JTextField.CENTER);
    }
}



