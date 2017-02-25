package view;

import controller.ItemEditorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import model.Item;

/**
 * Panel for item editing.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class EditItem extends JPanel{
    JComboBox<String> items;
    JComboBox<String> attributes;
    JComboBox<Integer> attribute_values;
    JButton button_create;
    ItemEditorController item_controller;

    /**
     * Initiate an item editing panel.
     *
     * @param item_id Id of the item that will be edited.
     */
    public EditItem(int item_id) {
        super();
        item_controller = new ItemEditorController();

        attributes = new JComboBox<>();
        attributes.setPreferredSize(new Dimension(120,30));
        attributes.addItem("Intelligence");
        attributes.addItem("Wisdom");
        attributes.addItem("Armor Class");

        items = new JComboBox<>();
        items.setPreferredSize(new Dimension(100,30));
        items.addItem("Helmet");
        items.addItem("Armor");
        items.addItem("Shield");
        items.addItem("Ring");
        items.addItem("Belt");
        items.addItem("Boots");
        items.addItem("Weapon");

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                String item = (String) items.getSelectedItem();
                switch (item) {
                    case "Helmet":
                        attributes.removeAllItems();
                        attributes.addItem("Intelligence");
                        attributes.addItem("Wisdom");
                        attributes.addItem("Armor Class");
                        break;
                    case "Armor":
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        break;
                    case "Shield":
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        break;
                    case "Ring":
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        attributes.addItem("Strength");
                        attributes.addItem("Constitution");
                        attributes.addItem("Wisdom");
                        attributes.addItem("Charisma");
                        break;
                    case "Belt":
                        attributes.removeAllItems();
                        attributes.addItem("Constitution");
                        attributes.addItem("Strength");
                        break;
                    case "Boots":
                        attributes.removeAllItems();
                        attributes.addItem("Armor Class");
                        attributes.addItem("Dexterity");
                        break;
                    case "Weapon":
                        attributes.removeAllItems();
                        attributes.addItem("Attack Bonus");
                        attributes.addItem("Damage Bonus");
                        break;
                }
            }
        };

        items.addItemListener(itemListener);

        attribute_values = new JComboBox<>();
        attribute_values.setPreferredSize(new Dimension(40,30));
        attribute_values.addItem(1);
        attribute_values.addItem(2);
        attribute_values.addItem(3);
        attribute_values.addItem(4);
        attribute_values.addItem(5);

        button_create = new JButton("Edit Item");
        button_create.addActionListener(new EditItem.saveItem());

        Item item = item_controller.getItem(item_id);
        items.setSelectedItem(item.getType());
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
    class saveItem implements ActionListener
    {
        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String item = (String) items.getSelectedItem();
            String attribute = (String) attributes.getSelectedItem();
            int attribute_value = (Integer) attribute_values.getSelectedItem();
            boolean result = item_controller.updateItem(item, attribute, attribute_value);
            if(result)
                JOptionPane.showMessageDialog( Main.mainFrame, "Success");
            else
                JOptionPane.showMessageDialog( Main.mainFrame, "Fail");
        }
    }
}
