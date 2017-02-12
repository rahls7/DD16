package display;

/**
 * Created by Alleria on 2017/2/11.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import display.CreateMap;
import display.EditMap;
import model.Cell;


public class Map extends JPanel{
    private JPanel create_panel, edit_panel;

    public Map(){
        super(new GridLayout(1,0));

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

    private JComboBox<Integer> getMapList() {

        String content = "";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("d://map.txt"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                content += line;
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        JSONObject json_content = new JSONObject(content);
        JSONArray json_maps = json_content.getJSONArray("maps");
        JComboBox<Integer> maps = new JComboBox<Integer>();

        for (int i = 0; i < json_maps.length(); i++) {
            int map_id = json_maps.getJSONObject(i).getInt("id");
            maps.addItem(map_id);
        }
        return maps;
    }
}

