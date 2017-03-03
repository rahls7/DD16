package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Gives information about the Game. To be updated after the final build
 * Created by rahls7 on 2017/02/10
 */

public class About extends JPanel {
    public About() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Text
        String str = "Welcome to the Fantasy World of Dragon and Dungeons. This game is created by an exceptional team of" +
                "Developers using Java.";
        g.drawString(str, 50, 50);
    }
}

