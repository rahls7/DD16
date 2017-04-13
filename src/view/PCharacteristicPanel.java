package view;

import model.PCharacter;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alleria on 2017/3/11.
 */
public class PCharacteristicPanel extends JPanel implements Observer {

    private PCharacter pCharacter;
    private JLabel nameLabel, lvlLabel, hpLabel, acLabel, abLabel, dbLabel, maLabel;
    private JLabel nameTextField, lvlTextField, hpTextField, acTextField, abTextField, dbTextField, maTextField;
    private JLabel strLabel, dexLabel, conLabel, intLabel, wisLabel, chaLabel;
    private JLabel strTextField, dexTextField, conTextField, intTextField, wisTextField, chaTextField;

    /**
     * constructor of characteristic panel
     */
    public PCharacteristicPanel() {
        super();
        setLayout(null);
        nameLabel = setAttributesLabel("Name:", 0);
        lvlLabel = setAttributesLabel("Level:", 1);
        hpLabel = setAttributesLabel("Hit Point:", 2);
        acLabel = setAttributesLabel("Armor Class:", 3);
        abLabel = setAttributesLabel("Attack Bonus:", 4);
        dbLabel = setAttributesLabel("Damage Bonus:", 5);
        maLabel = setAttributesLabel("Multiple Attacks:", 6);
        nameTextField = setAttributes(0);
        lvlTextField = setAttributes(1);
        hpTextField = setAttributes(2);
        acTextField = setAttributes(3);
        abTextField = setAttributes(4);
        dbTextField = setAttributes(5);
        maTextField = setAttributes(6);
        strLabel = setStatsLabel("Stength:", 0);
        dexLabel = setStatsLabel("Dexterity:", 1);
        conLabel = setStatsLabel("Constitution:", 2);
        intLabel = setStatsLabel("Intelligence:", 3);
        wisLabel = setStatsLabel("Wisdom:", 4);
        chaLabel = setStatsLabel("Charisma:", 5);
        strTextField = setStats(0);
        dexTextField = setStats(1);
        conTextField = setStats(2);
        intTextField = setStats(3);
        wisTextField = setStats(4);
        chaTextField = setStats(5);
    }

    @Override
    /**
     * override the update function of observer
     * when the character get changed, update this observer
     */
    public void update(Observable o, Object arg) {
        removeAll();
        pCharacter = (PCharacter) o;
        nameTextField.setText(pCharacter.getName());
        lvlTextField.setText("" + pCharacter.getLevel());
        hpTextField.setText("" + pCharacter.getHitPoint());
        acTextField.setText("" + pCharacter.getArmorClass());
        abTextField.setText("" + pCharacter.getAttackBonus());
        dbTextField.setText("" + pCharacter.getDamageBonus());
        if (pCharacter.getMultipleAttacks() == 0)
            maTextField.setText("False");
        else
            maTextField.setText("True");

        strTextField.setText("" + pCharacter.getStrengthModifier());
        dexTextField.setText("" + pCharacter.getDexterityModifier());
        conTextField.setText("" + pCharacter.getConstitutionModifier());
        intTextField.setText("" + pCharacter.getIntelligenceModifier());
        wisTextField.setText("" + pCharacter.getWisdomModifier());
        chaTextField.setText("" + pCharacter.getBasicCharismaModifier());

        add(nameLabel);
        add(lvlLabel);
        add(hpLabel);
        add(acLabel);
        add(maLabel);
        add(abLabel);
        add(dbLabel);
        add(nameTextField);
        add(lvlTextField);
        add(hpTextField);
        add(dbTextField);
        add(acTextField);
        add(abTextField);
        add(maTextField);
        add(strLabel);
        add(dexLabel);
        add(conLabel);
        add(intLabel);
        add(wisLabel);
        add(chaLabel);
        add(strTextField);
        add(dexTextField);
        add(conTextField);
        add(intTextField);
        add(wisTextField);
        add(chaTextField);
        validate();
        repaint();
    }

    /**
     * clean the panel up
     */
    public void clean() {
        removeAll();
        validate();
        repaint();
    }

    /**
     * Setter functions for Attributes.
     *
     * @param name name for Jlabel
     * @param x location
     * @return label Jlabel used for attributes
     */

    public JLabel setAttributesLabel(String name, int x) {
        JLabel label = new JLabel(name);
        label.setBounds(10, 20 + x * 20, 150, 20);
        return label;
    }

    public JLabel setAttributes(int x) {
        JLabel label = new JLabel("1");
        label.setBounds(160, 20 + x * 20, 50, 20);
        return label;
    }

    public JLabel setStatsLabel(String name, int x) {
        JLabel label = new JLabel(name);
        label.setBounds(350, 20 + x * 20, 150, 20);
        return label;
    }

    public JLabel setStats(int x) {
        JLabel label = new JLabel("2");
        label.setBounds(500, 20 + x * 20, 50, 20);
        return label;
    }
}
