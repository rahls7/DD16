package build1.character;

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
public class CharacterView extends JPanel{

    private CharacterController characterController;

    CharacterView(){

        super();
        setSize(1600,600);
        setLayout(null);
        characterController = new CharacterController();

        // Create AttributeLabels
        add(new AttributeLabel("Name:", 0));
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

        //Create Modifier Labels and the TextFiels of Stats
        for (int i=0; i<7; i++) {
            add(new StatsLabel("modifier:", i));
            for (int j = 0; j < 2; j++)
                add(new StatsTextField(i, j));
        }

        //Create AttributeTextFields
        add(new AttributeTextField(0));
        for (int i=2; i<8; i++){
            add(new AttributeTextField(i));
        }

        //Create buttons
        JButton rollButton = new JButton("Roll");
        rollButton.setBounds(630, 370, 100, 50);
        add(rollButton);
        TheDice theDice = new TheDice();
        rollButton.addActionListener(theDice);

        JButton createButton = new JButton("Create");
        createButton.setBounds(630,450,100,50);
        add(createButton);

        //backpack
        add(createItemJLabel("Equipment", 800, 50));
        add(createItemJLabel("Backpack", 1050, 50));
        add(createItemJLabel("Item", 1300, 50));
        add(createItemJButton("Take off", 850, 300));
        add(createItemJButton("Put on", 1100, 300));
        add(createItemJButton("Delete", 1100, 370));
        add(createItemJButton("Pick", 1350, 300));
        String[] equipment = {"Helmet", "Armor", "Shield", "Ring", "Belt", "Boots", "Weapon"};
        JComboBox equipmentComboBox = new JComboBox(equipment);
        equipmentComboBox.setBounds(800, 100, 150, 50);
        equipmentComboBox.setMaximumRowCount(7);
        add(equipmentComboBox);
        String[] backpack = {"Helmet:gold","Armor:silver","Shield:bronze","Ring:king","Belt:jade","Boots:tiger","Weapon:sword","Weapon:blade"};
        JComboBox backpackJComboBox = new JComboBox(backpack);
        backpackJComboBox.setBounds(1050, 100, 150, 50);
        backpackJComboBox.setMaximumRowCount(7);
        add(backpackJComboBox);
        String[] item = {"Helmet:shit","Armor:bullshit","shield:wood"};
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

    class TheDice implements ActionListener{

        public void actionPerformed(ActionEvent event){
            ArrayList arrayList = new ArrayList();
            arrayList = characterController.getEquipment();
            System.out.println(arrayList.get(0));
//            int dice = 0;
//            int modifier = 0;
//            Random rand = new Random();
//            StatsTextField statsTextField ;
//            for (int i=0; i<7; i++)
//                for (int j=0; j<2; j++){
//                    // Stats
//                    for (int k=0; k<4; k++) dice = dice + rand.nextInt(6) + 1;
//                    statsTextField = new StatsTextField(i,0);
//                    statsTextField.setText(""+dice);
//                    add(statsTextField);
//                    //Modifier
//                    modifier = dice / 2 -5;
//                    statsTextField = new StatsTextField(i,1);
//                    statsTextField.setText(""+modifier);
//                    add(statsTextField);
//                    dice = 0;
//                    modifier = 0;
//                }
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
