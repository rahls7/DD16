package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MapEditorController;
import org.json.JSONArray;

/**
 * Create a panel for map editor.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class Map extends JPanel{
    private JPanel create_panel, edit_panel;
    private MapEditorController map_controller;
    /**
     * Initiate a map editor panel including two panels: map creation panel and map editing panel.
     */
    public Map(){
        super(new GridLayout(1,0));
        map_controller = new MapEditorController();

        JTextField textfield_width = new JTextField("8");
        JTextField textfield_height = new JTextField("8");
        textfield_width.setPreferredSize(new Dimension(30, 30));
        textfield_height.setPreferredSize(new Dimension(30, 30));
        JLabel label_times = new JLabel("X");

        JButton button_create = new JButton("Create Map");
        button_create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(textfield_width.getText());
                int height = Integer.parseInt(textfield_height.getText());
                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new CreateMap(width, height), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);
            }
        });


        create_panel = new JPanel();
        create_panel.setBorder(BorderFactory.createLineBorder(Color.black));


        create_panel.add(textfield_width);
        create_panel.add(label_times);
        create_panel.add(textfield_height);
        create_panel.add(button_create);


        edit_panel = new JPanel();
        edit_panel.setBorder(BorderFactory.createLineBorder(Color.black));

        JComboBox<Integer> maps = getMapList();
        edit_panel.add(maps);

        JButton button_edit = new JButton("Edit Map");
        button_edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int map_id = (int)maps.getSelectedItem();

                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new EditMap(map_id), BorderLayout.CENTER);
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

    /**
     * Get map list from the file.
     *
     * @return List of maps.
     */
    private JComboBox<Integer> getMapList() {

        JSONArray json_maps = map_controller.getMapList();
        JComboBox<Integer> maps = new JComboBox<Integer>();

        for (int i = 0; i < json_maps.length(); i++) {
            int map_id = json_maps.getJSONObject(i).getInt("id");
            maps.addItem(map_id);
        }
        return maps;
    }
}

