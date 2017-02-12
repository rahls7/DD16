package display;

/**
 * Created by Alleria on 2017/2/11.
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class About  extends JPanel{
    public About(){
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Text
        g.drawString("SOEN 6441",50,50);
    }
}

