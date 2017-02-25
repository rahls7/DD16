package view;

import controller.CharacterEditorController;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

/**
 * Created by Silas on 2017/2/10.
 */
public class EditCharacter extends JPanel{

    private CharacterEditorController characterController;
    public JButton createButton, confirmButton,  takeoffButton, putonButton, deleteButton, pickButton;
    public JTextField nameTextField;
    public JTextField[] attributeTextField = new JTextField[6];
    public JTextField[][] statsTextField = new JTextField[7][2];
    String id;
    String name;
    int[] attribute = new int[6];
    int[][] stats = new int[7][2];

    public EditCharacter(String id){

        super();
        setSize(1600,600);
        setLayout(null);
        this.id = id;
        characterController = new CharacterEditorController(id);

        // Create AttributeLabels
        add(new AttributeLabel("name:", 0));
        add(new AttributeLabel("level:", 2));
        add(new AttributeLabel("Hit Point:", 3));
        add(new AttributeLabel("Armor Class:", 4));
        add(new AttributeLabel("Attack Bonus:", 5));
        add(new AttributeLabel("Damage Bonus:", 6));
        add(new AttributeLabel("Multiple Attacks:", 7));

        // Create StatsLabels
        add(new StatsLabel("Ability:", 0));
        add(new StatsLabel("Strength:", 1));
        add(new StatsLabel("Dexterity:", 2));
        add(new StatsLabel("Constitution:", 3));
        add(new StatsLabel("Intelligence:", 4));
        add(new StatsLabel("Wisdom:", 5));
        add(new StatsLabel("Charisma:", 6));

        //read data
        name = characterController.getName();
        attribute = characterController.getAttributes();
        stats = characterController.getStats();

        //Create Modifier Labels and the TextFiels of Stats
        for (int i=0; i<7; i++) {
            add(new StatsLabel("modifier:", i));
            for (int j = 0; j < 2; j++) {
                statsTextField[i][j] = new StatsTextField(i, j);
                statsTextField[i][j].setText(Integer.toString(stats[i][j]));
                add(statsTextField[i][j]);
            }
        }

        //Create AttributeTextFields
        nameTextField = new AttributeTextField(0);
        add(nameTextField);
        for (int i=0; i<6; i++){
            attributeTextField[i] = new AttributeTextField(i+2);
            attributeTextField[i].setText(Integer.toString(attribute[i]));
            add(attributeTextField[i]);
        }

        //Create buttons
        Handler handler = new Handler();
        createButton = new JButton("Edit");
        createButton.setBounds(630, 370, 100, 50);
        add(createButton);
        createButton.addActionListener(handler);

        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(630,450,100,50);
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
        add(takeoffButton );
        add(putonButton);
        add(deleteButton);
        add(pickButton);
        String[] equipment = {}; //{"Helmet", "Armor", "Shield", "Ring", "Belt", "Boots", "Weapon"};
        JComboBox equipmentComboBox = new JComboBox(equipment);
        equipmentComboBox.setBounds(800, 100, 150, 50);
        equipmentComboBox.setMaximumRowCount(7);
        add(equipmentComboBox);
        String[] backpack = {}; //{"Helmet:gold","Armor:silver","Shield:bronze","Ring:king","Belt:jade","Boots:tiger","Weapon:sword","Weapon:blade"};
        JComboBox backpackJComboBox = new JComboBox(backpack);
        backpackJComboBox.setBounds(1050, 100, 150, 50);
        backpackJComboBox.setMaximumRowCount(7);
        add(backpackJComboBox);
        String[] item = {}; //{"Helmet:shit","Armor:bullshit","shield:wood"};
        JComboBox itemJComboBox = new JComboBox(item);
        itemJComboBox.setBounds(1300, 100, 150, 50);
        itemJComboBox.setMaximumRowCount(7);
        add(itemJComboBox);
    }

    JLabel createItemJLabel(String name, int x, int y){
        JLabel label = new JLabel(name);
        label.setBounds(x, y, 100, 50);
        label.setFont(new Font("dialog", 0, 20));
        return label;
    }

    JButton createItemJButton(String name, int x, int y){
        JButton button = new JButton(name);
        button.setBounds(x, y, 100, 50);
        return button;
    }

    class Handler implements ActionListener{

        public void actionPerformed(ActionEvent event){
            if (event.getSource()==createButton){
                String name = nameTextField.getText();
                System.out.println(characterController.getName());

                validate();
                repaint();
            }
            else if (event.getSource()==confirmButton){

            }else if (event.getSource()==takeoffButton){

            }else if (event.getSource()==putonButton){

            }else if (event.getSource()==deleteButton){

            }else if (event.getSource()==pickButton){

            }
//
        }
    }

}


class StatsLabel extends JLabel{
    StatsLabel(String name, int number){
        setText(name);
        if (name.equals("modifier:"))
            setBounds(560, number*50+20, 120, 40);
        else
            setBounds(330, number*50+20, 120, 40);
        setFont(new Font("dialog", 0, 15));
    }
}

class StatsTextField extends JTextField{
    StatsTextField(int i, int j){
        setVisible(true);
        setFont(new Font("dialog", 0, 15));
        setLocation(430+j*200,i*50+20);
        setSize(100,40);
        setHorizontalAlignment(JTextField.CENTER);
    }
}

class AttributeLabel extends JLabel{
    AttributeLabel(String name, int number){
        setText(name);
        setBounds(20, number*50+20, 120, 40);
        setFont(new Font("dialog", 0, 15));
    }
}

class  AttributeTextField extends JTextField{
    AttributeTextField(int i){
        setVisible(true);
        setFont(new Font("dialog", 0, 15));
        setLocation(140,i*50+20);
        setSize(100,40);
        setHorizontalAlignment(JTextField.CENTER);
    }
}



