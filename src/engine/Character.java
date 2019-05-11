package engine;

public class Character implements  java.io.Serializable{
    /*Egy karakternek az alábbi tulajdonságokkal kell rendelkeznie
     *Karakter kreálásakor ezek a legfőbb tulajdonságok, majd ehhez még jönnek
     * az osztályspecifikus módosítók
     */
    // Character details
    private String name="";
    private Classes characterClass;
    private Races race;
    // Attributes
    private int strength, dexterity, health, intelligence, luck, constitution;
    private int[] equipmentsId;
    // Progress
    private int experiencePoints;
    private int level;
    public enum Classes {
        Warrior("Warrior"), Scout("Scout"), Mage("Mage");
        private final String Classes;
        Classes(String s) {
            Classes = s;
        }
        public String toString() {
            return Classes;
        }
        }
    public enum Races{Human("Human"), Elf("Elf"), Dwarf("Dwarf"), Gnome("Gnome"), Goblin("Goblin"), Dark_Elf("Dark_elf"), Orc("Orc"), Demon("Demon");
        private final String Races;
        Races(String s) {
            Races = s;
        }
        public String toString() {
            return Races;
        }
    }

    public Character(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Classes getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(Classes characterClass) {
        this.characterClass = characterClass;
    }

    public Races getRace() {
        return race;
    }

    public void setRace(Races race) {
        this.race = race;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int[] getEquipmentsId() {
        return equipmentsId;
    }

    public void setEquipmentsId(int[] equipmentsId) {
        this.equipmentsId = equipmentsId;
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

}
