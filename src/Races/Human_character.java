package Races;

import database.Modifiers;

public class Human_character extends Character {
    private int modifier;
    private final static int character_id=5;
    public Human_character(String name, int strength, int agility, int health, int constitution, int intelligence, int intuition, int charisma) {
        super(name, strength, agility, health, constitution, intelligence, intuition, charisma);
        Modifiers modify=new Modifiers();
        this.modifier=modify.getStrengthModifier(character_id);
        this.setStrength(this.getStrength()+this.getModifier());
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public static int getCharacter_id() {
        return character_id;
    }

}
