package build1.character;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Silas on 2017/2/10.
 */
public class Test extends JFrame{
    private ArrayList string;
    Test(){
         string = new ArrayList();
        setSize(1600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        JTextField textField1 = new JTextField("SansSerif");

        add(new CharacterView());

        setVisible(true);
    }

    public static void main(String[] args){
        new Test();
    }
}

