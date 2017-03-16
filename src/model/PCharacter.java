package model;

import java.util.ArrayList;

/**
 * Created by mo on 2017-03-08.
 */
public class PCharacter extends PCellContent{

    private int category; //0: friend, 1: enemy, 2: player
    private String id;
    private String name;
    private ArrayList<PItem> equipment;
    private ArrayList<PItem> backpack;
    private int strength, dexterity, constitution, intelligence, wisdom, charisma;
    private int basicStrengthModifier, basicDexterityModifier, basicConstitutionModifier, basicIntelligenceModifier,
            basicWisdomModifier, basicCharismaModifier;
    private int strengthModifier, dexterityModifier, constitutionModifier, intelligenceModifier, wisdomModifier,
            charismModifier;
    private int basicLevel, basicHitPoint, basicArmorClass, basicAttackBonus, basicDamageBonus, basicMultipleAttacks;
    private int level, hitPoint, armorClass, attackBonus, damageBonus, multipleAttacks;

    public PCharacter(String id, String isHostile){
        type = "PLAYER";
        CharacterIO characterIO = new CharacterIO();
        Character character = characterIO.getCharacter(id);
        this.id = character.getId();
        this.name = character.getName();

        this.equipment = new ArrayList<PItem>();
        for(Item item : character.getEquipment()) {
            PItem i = new PItem(item.getSaveId(), item.getType(), item.getAttribute(), item.getAttributeValue());
            this.equipment.add(i);
        }

        this.backpack = new ArrayList<PItem>();
        for(Item item : character.getBackpack()) {
            PItem i = new PItem(item.getSaveId(), item.getType(), item.getAttribute(), item.getAttributeValue());
            this.backpack.add(i);
        }

        int stats[][] = new int[6][2];
        stats = character.getStats();
        strength = stats[0][0];;
        dexterity = stats[1][0];
        constitution = stats[2][0];
        intelligence = stats[3][0];
        wisdom = stats[4][0];
        charisma = stats[5][0];

        strengthModifier = stats[0][1];
        dexterityModifier = stats[1][1];
        constitutionModifier = stats[2][1];
        intelligenceModifier = stats[3][1];
        wisdomModifier = stats[4][1];
        charismModifier=stats[5][1];

        int basicStats[][] = new int[6][2];
        basicStats = character.getBasicStats();
        basicStrengthModifier = basicStats[0][1];
        basicDexterityModifier = basicStats[1][1];
        basicConstitutionModifier = basicStats[2][1];
        basicIntelligenceModifier = basicStats[3][1];
        basicWisdomModifier = basicStats[4][1];
        basicCharismaModifier=basicStats[5][1];

        int attributes[] = new int[6];
        attributes = character.getAttributes();
        level = attributes[0];
        hitPoint = attributes[1];
        armorClass = attributes[2];
        attackBonus = attributes[3];
        damageBonus = attributes[4];
        multipleAttacks = attributes[5];

        int basicAttributes[] = new int[6];
        basicAttributes = character.getBasicAttributes();
        basicLevel = basicAttributes[0];
        basicHitPoint = basicAttributes[1];
        basicArmorClass = basicAttributes[2];
        basicAttackBonus = basicAttributes[3];
        basicDamageBonus = basicAttributes[4];
        basicMultipleAttacks = basicAttributes[5];

        if(isHostile.equals("1"))
            this.category = 1;
        else if(isHostile.equals("0"))
            this.category = 0;
        else
            this.category = 2;
    }

    /**
     * recalculate the stats of character
     */
    public void recalculateStats() {

        boolean weaponEquipped = false;
        strengthModifier = basicStrengthModifier;
        dexterityModifier = basicDexterityModifier;
        constitutionModifier = basicConstitutionModifier;
        intelligenceModifier = basicIntelligenceModifier;
        wisdomModifier = basicWisdomModifier;
        charismModifier = basicCharismaModifier;

        // first loop
        for (PItem item : equipment) {
            if (item.getType().equals("Weapon"))
                weaponEquipped = true;
            switch (item.getAttribute()) {
                case "Strength":
                    strengthModifier = strengthModifier + item.getAttributeValue();
                    break;
                case "Dexterity":
                    dexterityModifier = dexterityModifier + item.getAttributeValue();
                    break;
                case "Constitution":
                    constitutionModifier = constitutionModifier + item.getAttributeValue();
                    break;
                case "Intelligence":
                    intelligenceModifier = intelligenceModifier + item.getAttributeValue();
                    break;
                case "Wisdom":
                    wisdomModifier = wisdomModifier + item.getAttributeValue();
                    break;
                case "Charisma":
                    charismModifier = charismModifier + item.getAttributeValue();
                    break;
            }
        }

        //hitpoint
        hitPoint = (basicHitPoint + constitutionModifier) * level;
        //armor class
        armorClass = 10 + dexterityModifier;
        //attack bonus
        attackBonus = level;
        //damage bonus
        damageBonus = strengthModifier;

        //second loop
        for (PItem item : equipment) {
            switch (item.getAttribute()) {
                case "Hit Point":
                    hitPoint = hitPoint + item.getAttributeValue();
                    break;
                case "Armor Class":
                    armorClass = armorClass + item.getAttributeValue();
                    break;
                case "Attack Bonus":
                    attackBonus = attackBonus + item.getAttributeValue();
                    break;
                case "Damage Bonus":
                    damageBonus = damageBonus + item.getAttributeValue();
                    break;
            }
        }

        //multiple attack
        if (attackBonus > 6)
            multipleAttacks = 1;
        else
            attackBonus = 0;

        if (!weaponEquipped)
            damageBonus = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PItem> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<PItem> equipment) {
        this.equipment = equipment;
    }

    public ArrayList<PItem> getBackpack() {
        return backpack;
    }

    public void setBackpack(ArrayList<PItem> backpack) {
        this.backpack = backpack;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getBasicStrengthModifier() {
        return basicStrengthModifier;
    }

    public void setBasicStrengthModifier(int basicStrengthModifier) {
        this.basicStrengthModifier = basicStrengthModifier;
    }

    public int getBasicDexterityModifier() {
        return basicDexterityModifier;
    }

    public void setBasicDexterityModifier(int basicDexterityModifier) {
        this.basicDexterityModifier = basicDexterityModifier;
    }

    public int getBasicConstitutionModifier() {
        return basicConstitutionModifier;
    }

    public void setBasicConstitutionModifier(int basicConstitutionModifier) {
        this.basicConstitutionModifier = basicConstitutionModifier;
    }

    public int getBasicIntelligenceModifier() {
        return basicIntelligenceModifier;
    }

    public void setBasicIntelligenceModifier(int basicIntelligenceModifier) {
        this.basicIntelligenceModifier = basicIntelligenceModifier;
    }

    public int getBasicWisdomModifier() {
        return basicWisdomModifier;
    }

    public void setBasicWisdomModifier(int basicWisdomModifier) {
        this.basicWisdomModifier = basicWisdomModifier;
    }

    public int getBasicCharismaModifier() {
        return basicCharismaModifier;
    }

    public void setBasicCharismaModifier(int basicCharismaModifier) {
        this.basicCharismaModifier = basicCharismaModifier;
    }

    public int getStrengthModifier() {
        return strengthModifier;
    }

    public void setStrengthModifier(int strengthModifier) {
        this.strengthModifier = strengthModifier;
    }

    public int getDexterityModifier() {
        return dexterityModifier;
    }

    public void setDexterityModifier(int dexterityModifier) {
        this.dexterityModifier = dexterityModifier;
    }

    public int getConstitutionModifier() {
        return constitutionModifier;
    }

    public void setConstitutionModifier(int constitutionModifier) {
        this.constitutionModifier = constitutionModifier;
    }

    public int getIntelligenceModifier() {
        return intelligenceModifier;
    }

    public void setIntelligenceModifier(int intelligenceModifier) {
        this.intelligenceModifier = intelligenceModifier;
    }

    public int getWisdomModifier() {
        return wisdomModifier;
    }

    public void setWisdomModifier(int wisdomModifier) {
        this.wisdomModifier = wisdomModifier;
    }

    public int getCharismModifier() {
        return charismModifier;
    }

    public void setCharismModifier(int charismModifier) {
        this.charismModifier = charismModifier;
    }

    public int getBasicLevel() {
        return basicLevel;
    }

    public void setBasicLevel(int basicLevel) {
        this.basicLevel = basicLevel;
    }

    public int getBasicHitPoint() {
        return basicHitPoint;
    }

    public void setBasicHitPoint(int basicHitPoint) {
        this.basicHitPoint = basicHitPoint;
    }

    public int getBasicArmorClass() {
        return basicArmorClass;
    }

    public void setBasicArmorClass(int basicArmorClass) {
        this.basicArmorClass = basicArmorClass;
    }

    public int getBasicAttackBonus() {
        return basicAttackBonus;
    }

    public void setBasicAttackBonus(int basicAttackBonus) {
        this.basicAttackBonus = basicAttackBonus;
    }

    public int getBasicDamageBonus() {
        return basicDamageBonus;
    }

    public void setBasicDamageBonus(int basicDamageBonus) {
        this.basicDamageBonus = basicDamageBonus;
    }

    public int getBasicMultipleAttacks() {
        return basicMultipleAttacks;
    }

    public void setBasicMultipleAttacks(int basicMultipleAttacks) {
        this.basicMultipleAttacks = basicMultipleAttacks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    public int getMultipleAttacks() {
        return multipleAttacks;
    }

    public void setMultipleAttacks(int multipleAttacks) {
        this.multipleAttacks = multipleAttacks;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void addBackpack(PItem item) {
        this.backpack.add(item);
    }

    public void levelUp() {
        this.level++;
        recalculateStats();
    }

    public void addToBackpack(PItem item) {this.backpack.add(item);}

  
}
