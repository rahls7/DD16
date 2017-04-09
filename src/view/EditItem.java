package view;

import controller.ItemEditorController;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Panel for item editing.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class EditItem extends JPanel {
    JComboBox<String> items;
    JComboBox<String> attributes;
    JComboBox<Integer> attribute_values;
    JButton button_create;
    ItemEditorController item_controller;
    JCheckBox freezing;
    JCheckBox burning;
    JCheckBox slaying;
    JCheckBox frightening;
    JCheckBox pacifying;

    /**
     * Initiate an item editing panel.
     *
     * @param item_id Id of the item that will be edited.
     */
    public EditItem(int item_id) {
        super();
        item_controller = new ItemEditorController();

        attributes = new JComboBox<>();
        attributes.setPreferredSize(new Dimension(140, 30));
        attributes.addItem("Intelligence");
        attributes.addItem("Wisdom");
        attributes.addItem("Armor Class");

        items = new JComboBox<>();
        items.setPreferredSize(new Dimension(140, 30));
        items.addItem("Helmet");
        items.addItem("Armor");
        items.addItem("Shield");
        items.addItem("Ring");
        items.addItem("Belt");
        items.addItem("Boots");
        items.addItem("Ranged Weapon");
        items.addItem("Melee Weapon");

        freezing =  new JCheckBox("Freezing");
        burning = new JCheckBox("Burning");
        slaying = new JCheckBox("Slaying");
        frightening = new JCheckBox("Frightening");
        pacifying = new JCheckBox("Pacifying");



        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                String item = (String) items.getSelectedItem();
                switch (item) {
                    case "Helmet":
                        remove(freezing);
                        remove(burning);
                        remove(slaying);
                        remove(frightening);
                        remove(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Intelligence");
                        attributes.addItem("Wisdom");
                        attributes.addItem("Armor Class");
                        revalidate();
                        repaint();
                        break;
                    case "Armor":
                        remove(freezing);
                        remove(burning);
                        remove(slaying);
                        remove(frightening);
                        remove(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        revalidate();
                        repaint();
                        break;
                    case "Shield":
                        remove(freezing);
                        remove(burning);
                        remove(slaying);
                        remove(frightening);
                        remove(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        revalidate();
                        repaint();
                        break;
                    case "Ring":
                        remove(freezing);
                        remove(burning);
                        remove(slaying);
                        remove(frightening);
                        remove(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        attributes.addItem("Strength");
                        attributes.addItem("Constitution");
                        attributes.addItem("Wisdom");
                        attributes.addItem("Charisma");
                        revalidate();
                        repaint();
                        break;
                    case "Belt":
                        remove(freezing);
                        remove(burning);
                        remove(slaying);
                        remove(frightening);
                        remove(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Constitution");
                        attributes.addItem("Strength");
                        revalidate();
                        repaint();
                        break;
                    case "Boots":
                        remove(freezing);
                        remove(burning);
                        remove(slaying);
                        remove(frightening);
                        remove(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        attributes.addItem("Dexterity");
                        revalidate();
                        repaint();
                        break;
                    case "Ranged Weapon":
                        add(freezing);
                        add(burning);
                        add(slaying);
                        add(frightening);
                        add(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Attack Bonus");
                        attributes.addItem("Damage Bonus");
                        revalidate();
                        repaint();
                        break;
                    case "Melee Weapon":
                        add(freezing);
                        add(burning);
                        add(slaying);
                        add(frightening);
                        add(pacifying);
                        attributes.removeAllItems();
                        attributes.addItem("Attack Bonus");
                        attributes.addItem("Damage Bonus");
                        revalidate();
                        repaint();
                        break;
                }
            }
        };

        items.addItemListener(itemListener);

        attribute_values = new JComboBox<>();
        attribute_values.setPreferredSize(new Dimension(100, 40));
        attribute_values.addItem(1);
        attribute_values.addItem(2);
        attribute_values.addItem(3);
        attribute_values.addItem(4);
        attribute_values.addItem(5);

        button_create = new JButton("Edit Item");
        button_create.addActionListener(new EditItem.saveItem());

        Item item = item_controller.getItem(item_id);
        items.setSelectedItem(item.getType());
        if(item.getType().equals("Ranged Weapon") || item.getType().equals("Melee Weapon")) {
            String[] parts = item.getAttribute().split(",");
            System.out.println("wwww " + item.getAttribute());
            if(parts.length != 1){
                String[] atts = parts[1].split(" ");
                attributes.setSelectedItem(parts[0]);
                for(int i = 1; i < atts.length; i++) {
                    if(atts[i].equals("Freezing"))
                        freezing.setSelected(true);
                    if(atts[i].equals("Burning"))
                        burning.setSelected(true);
                    if(atts[i].equals("Slaying"))
                        slaying.setSelected(true);
                    if(atts[i].equals("Frightening"))
                        frightening.setSelected(true);
                    if(atts[i].equals("Pacifying"))
                        pacifying.setSelected(true);
                }
            }

        }
        else
            attributes.setSelectedItem(item.getAttribute());
        attribute_values.setSelectedItem(item.getAttributeValue());

        add(items);
        add(attributes);
        add(attribute_values);
        add(button_create);


    }

    /**
     * Action performed in order to save an item.
     */
    class saveItem implements ActionListener {
        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String item = (String) items.getSelectedItem();
            String attribute = (String) attributes.getSelectedItem();
            int attribute_value = (Integer) attribute_values.getSelectedItem();

            if(item.equals("Ranged Weapon") || item.equals("Melee Weapon")) {
                attribute += ",";
                if(freezing.isSelected()){
                    attribute += " " + "Freezing";
                }
                if(burning.isSelected()){
                    attribute += " " + "Burning";
                }
                if(slaying.isSelected()){
                    attribute += " " + "Slaying";
                }
                if(frightening.isSelected()){
                    attribute += " " + "Frightening";
                }
                if(pacifying.isSelected()){
                    attribute += " " + "Pacifying";
                }
            }

            boolean result = item_controller.updateItem(item, attribute, attribute_value);
            if (result)
                JOptionPane.showMessageDialog(Main.mainFrame, "Success");
            else
                JOptionPane.showMessageDialog(Main.mainFrame, "Fail");
        }
    }
}
