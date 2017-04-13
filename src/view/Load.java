package view;

import controller.LoadController;
import controller.PlayController;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fish on 2017/4/10.
 */
public class Load extends JPanel{
    private JPanel load_panel;
    private LoadController loadController;

    public Load(){
        super(new GridLayout(1, 0));
        loadController = new LoadController();

        load_panel = new JPanel();
        load_panel.setBorder(BorderFactory.createLineBorder(Color.black));
        JComboBox<Integer> plays = getPlayList();
        plays.setPreferredSize(new Dimension(200, 50));
        plays.setBorder(BorderFactory.createTitledBorder("Game File :"));
        load_panel.add(plays);

        JButton load_button = new JButton("Load Game");
        load_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int play_id = (int) plays.getSelectedItem();

                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new Play(play_id), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);
            }
        });
        load_panel.add(load_button);

        add(load_panel);
    }

    /**
     * Get play list from play file.
     *
     * @return List of play files.
     */
    private JComboBox<Integer> getPlayList() {

        JSONArray json_plays = loadController.getPlayList();
        JComboBox<Integer> plays = new JComboBox<Integer>();

        for (int i = 0; i < json_plays.length(); i++) {
            int item_id = json_plays.getJSONObject(i).getInt("id");
            plays.addItem(item_id);
        }
        return plays;
    }
}
