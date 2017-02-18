package display;

/**
 * Created by Alleria on 2017/2/17.
 */

import javax.swing.*;
import Controller.ItemEditorController;
import org.json.JSONArray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

public class Item extends JPanel {

    private JPanel create_panel, edit_panel;
    private ItemEditorController item_controller;

    public Item() {
        super(new GridLayout(1,0));
        item_controller = new ItemEditorController();

        create_panel = new JPanel();
        create_panel.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton button_create = new JButton("Create Item");
        button_create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new CreateItem(), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);
            }
        });
        create_panel.add(button_create);

        edit_panel = new JPanel();
        edit_panel.setBorder(BorderFactory.createLineBorder(Color.black));

        JComboBox<Integer> items = getItemList();
        edit_panel.add(items);

        JButton button_edit = new JButton("Edit Item");
        button_edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int item_id = (int)items.getSelectedItem();
                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new EditItem(item_id), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);
            }
        });
        edit_panel.add(button_edit);

        add(create_panel);
        add(edit_panel);
    }
    private JComboBox<Integer> getItemList() {

        JSONArray json_items = item_controller.getItemList();
        JComboBox<Integer> items = new JComboBox<Integer>();

        for (int i = 0; i < json_items.length(); i++) {
            int item_id = json_items.getJSONObject(i).getInt("id");
            items.addItem(item_id);
        }
        return items;
    }
}
