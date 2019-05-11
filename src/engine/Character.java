package engine;

public class Character implements  java.io.Serializable{
    /*Egy karakternek az alábbi tulajdonságokkal kell rendelkeznie
     *Karakter kreálásakor ezek a legfőbb tulajdonságok, majd ehhez még jönnek
     * az osztályspecifikus módosítók
     */



    // Character details
    private String name="";
    private String background = "";
    private String size = "";
    private Boolean isMale;
    private Classes LotrClass;
    private Races race;
    // Attributes
    private int strength, agility, health, intelligence, intuition, charisma, constitution;
    private int strBonus,agiBonus, hthBonus, intBonus,innBonus, chaBonus, conBonus;
    // Equipment
    private int copper, silver, gold, mithril;
    private String equipment = "";
    // Trait boxes
    private String personalityTraits = "";
    private String ideals = "";
    private String bonds = "";
    private String flaws = "";
    private String featureTraits = "";

    // Progress
    private int experiencePoints;
    private int level;
    private int maxHitPoints;

    public enum Classes {Barbarian, Cleric, Fighter, Monk, Paladin, Ranger, Rogue, Sorcerer, Wizard}
    public enum Races{Dwarf, Greyelf, Halfelf, Hobbit, Human, Nobleelf, Umli, Woodelf,Woodman}

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
    public int getStrBonus() {
        return strBonus;
    }

    public void setStrBonus(int strBonus) {
        this.strBonus = strBonus;
    }

    public int getAgiBonus() {
        return agiBonus;
    }

    public void setAgiBonus(int agiBonus) {
        this.agiBonus = agiBonus;
    }

    public int getHthBonus() {
        return hthBonus;
    }

    public void setHthBonus(int hthBonus) {
        this.hthBonus = hthBonus;
    }

    public int getIntBonus() {
        return intBonus;
    }

    public void setIntBonus(int intBonus) {
        this.intBonus = intBonus;
    }

    public int getInnBonus() {
        return innBonus;
    }

    public void setInnBonus(int innBonus) {
        this.innBonus = innBonus;
    }

    public int getChaBonus() {
        return chaBonus;
    }

    public void setChaBonus(int chaBonus) {
        this.chaBonus = chaBonus;
    }

    public int getConBonus() {
        return conBonus;
    }

    public void setConBonus(int conBonus) {
        this.conBonus = conBonus;
    }

    public int getCopper() {
        return copper;
    }

    public void setCopper(int copper) {
        this.copper = copper;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getMithril() {
        return mithril;
    }

    public void setMithril(int mithril) {
        this.mithril = mithril;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getIdeals() {
        return ideals;
    }

    public void setIdeals(String ideals) {
        this.ideals = ideals;
    }

    public String getBonds() {
        return bonds;
    }

    public void setBonds(String bonds) {
        this.bonds = bonds;
    }

    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }

    public String getFeatureTraits() {
        return featureTraits;
    }

    public void setFeatureTraits(String featureTraits) {
        this.featureTraits = featureTraits;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }

    public Classes getLotrClass() {
        return LotrClass;
    }

    public void setLotrClass(Classes lotrClass) {
        LotrClass = lotrClass;
    }

    public Races getRace() {
        return race;
    }

    public void setRace(Races race) {
        this.race = race;
    }


}
