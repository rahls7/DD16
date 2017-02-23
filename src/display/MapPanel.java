package display;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Ruijia on 2017/2/19.
 */
public class MapPanel extends JPanel{
    private String map_id;
    private boolean isSelected;
    private JLabel map_id_label;
    private int index;

    public int getIndex(){return index;}
    public void setIndex(int i){this.index=i;}

    public String getMapId(){
        return map_id;
    }
    public void setMapId(String id){
        this.map_id=id;
        this.map_id_label.setText(id);
    }

    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.isSelected = true;
    }

    public boolean isselected() {
        return this.isSelected;
    }
    public void deselect() {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.isSelected = false;
    }

    public MapPanel(String map_id){
        this.map_id=map_id;
        map_id_label=new JLabel(map_id);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setPreferredSize(new Dimension(50,50));
        setBorder(blackline);
        add(map_id_label);
        //repaint();
    }

}
