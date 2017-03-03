package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mo on 2017-02-22.
 */
public class Character extends JPanel{
        private JPanel create_panel, edit_panel;
        /**
         * Initiate a map editor panel including two panels: map creation panel and map editing panel.
         */
        public Character(){
            super();
            setSize(2500,900);
            setLayout(null);

            JTextField  idTextField= new JTextField();
            idTextField.setBounds(150,0,150,50);
            JLabel idLabel = new JLabel("ID:");
            idLabel.setBounds(100,0,50,50);
            idLabel.setFont(new Font("dialog", 0, 20));
            JButton createButton = new JButton("Create Character");
            createButton.setBounds(300,0,150,50);

            createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Main.mainFrame.getContentPane().removeAll();
                    Main.mainFrame.getContentPane().add(new CreateCharacter(idTextField.getText()), BorderLayout.CENTER);
                    Main.mainFrame.getContentPane().doLayout();
                    repaint();
                    validate();
                    Main.mainFrame.setVisible(true);
                }
            });

            add(idLabel);
            add(idTextField);
            add(createButton);
//

            JTextField  idTextField1= new JTextField();
            idTextField1.setBounds(750,0,150,50);
            JLabel idLabel1 = new JLabel("ID:");
            idLabel1.setBounds(700,0,50,50);
            idLabel1.setFont(new Font("dialog", 0, 20));
            JButton editButton = new JButton("Edit Character");
            editButton.setBounds(900,0,150,50);

            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Main.mainFrame.getContentPane().removeAll();
                    Main.mainFrame.getContentPane().add(new EditCharacter(idTextField1.getText()), BorderLayout.CENTER);
                    Main.mainFrame.getContentPane().doLayout();
                    repaint();
                    validate();
                    Main.mainFrame.setVisible(true);
                }
            });
            add(editButton);
            add(idTextField1);
            add(idLabel1);

        }



}
