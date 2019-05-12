package engine;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Character implements  java.io.Serializable{
    /*Egy karakternek az alábbi tulajdonságokkal kell rendelkeznie
     *Karakter kreálásakor ezek a legfőbb tulajdonságok, majd ehhez még jönnek
     * az osztályspecifikus módosítók
     */
    // Character details
    private final SimpleStringProperty name=new SimpleStringProperty("");
    private Classes characterClass;
    private Races race;
    // Attributes
    private final SimpleIntegerProperty strength=new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty dexterity=new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty intelligence=new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty constitution=new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty luck=new SimpleIntegerProperty(0);
    // Progress
    private final SimpleIntegerProperty experiencePoints=new SimpleIntegerProperty(0);

    private final SimpleIntegerProperty level=new SimpleIntegerProperty(0);
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
    public Character(String name, int strength, int dexterity, int intelligence, int constitution, int luck, int experiencePoints, int level, Classes characterClass){
        this.characterClass = characterClass;
        this.name.set(name);
        this.strength.set(strength);
        this.dexterity.set(dexterity);
        this.intelligence.set(intelligence);
        this.constitution.set(constitution);
        this.luck.set(luck);
        this.experiencePoints.set(experiencePoints);
        this.level.set(level);
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

    public String getName(){return name.get();}

    public int getStrength() {
        return strength.get();
    }

    public int getDexterity() {
        return dexterity.get();
    }
    public int getIntelligence() {
        return intelligence.get();
    }

    public int getConstitution() {
        return constitution.get();
    }

    public int getLuck() {
        return luck.get();
    }

    public int getExperiencePoints() {
        return experiencePoints.get();
    }

    public int getLevel() {
        return level.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setStrength(int strength) {
        this.strength.set(strength);
    }

    public void setDexterity(int dexterity) {
        this.dexterity.set(dexterity);
    }

    public void setIntelligence(int intelligence) {
        this.intelligence.set(intelligence);
    }

    public void setConstitution(int constitution) {
        this.constitution.set(constitution);
    }

    public void setLuck(int luck) {
        this.luck.set(luck);
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints.set(experiencePoints);
    }

    public void setLevel(int level) {
        this.level.set(level);
    }
}
