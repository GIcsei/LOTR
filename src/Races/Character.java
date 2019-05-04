package Races;

public abstract class Character {
    /*Egy karakternek az alábbi tulajdonságokkal kell rendelkeznie
     *Karakter kreálásakor ezek a legfoőbb tulajdonságok, majd ehhez még jönnek
     * az osztályspecifikus módosítók
     */


    private String name;
    private int strength;
    private int agility;
    private int health;
    private int constitution;
    private int intelligence;
    private int intuition;
    private int charisma;

    public Character(){}


    public Character(String name, int strength, int agility, int health, int constitution, int intelligence, int intuition, int charisma) {
        this.name=name;
        this.strength = strength;
        this.agility = agility;
        this.health = health;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.intuition = intuition;
        this.charisma = charisma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public int getIntuition() {
        return intuition;
    }

    public void setIntuition(int intuition) {
        this.intuition = intuition;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void printAllTraits(){
        System.out.println("Name: "+getName()+"\nStrength: "+getStrength()+"\nAgility: "+getAgility()+"\nHealth: "
                +getHealth()+"\nConstitution: "+getConstitution()+"\nIntelligence: "+getIntelligence()+"\nIntuition: "+getIntuition()+"\nCharisma: "+getCharisma());
    }
}
