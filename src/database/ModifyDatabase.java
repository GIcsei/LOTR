package database;

import engine.Character;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ModifyDatabase {
    public ArrayList<Integer> getClassModifiers(String className){
        ArrayList<Integer> modifier=new ArrayList<>();
            try {
                Class loadedClass = Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            String connectionString = "jdbc:sqlite:races.db";
            try(Connection conn = DriverManager.getConnection(connectionString)) {
                String sql="SELECT * from Classes where Name=?";
                try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
                    pstmt.setString(1, className);
                    ResultSet rs  = pstmt.executeQuery();
                    while(rs.next()) {
                        modifier.add(rs.getInt("Id"));
                        modifier.add(rs.getInt("Strength"));
                        modifier.add(rs.getInt("Dexterity"));
                        modifier.add(rs.getInt("Intelligence"));
                        modifier.add(rs.getInt("Constitution"));
                        modifier.add(rs.getInt("Luck"));
                    }
                    rs.close();
                }
            }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return modifier;
    }
    public ArrayList<Integer> getRaceModifiers(String raceName){
        ArrayList<Integer> modifier=new ArrayList<>();
        try {
            Class loadedClass = Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String connectionString = "jdbc:sqlite:races.db";
        try(Connection conn = DriverManager.getConnection(connectionString)) {
            String sql="SELECT * from Races where Name=?";
            try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
                pstmt.setString(1, raceName);
                ResultSet rs  = pstmt.executeQuery();
                while(rs.next()) {
                    modifier.add(rs.getInt("Id"));
                    modifier.add(rs.getInt("Strength"));
                    modifier.add(rs.getInt("Dexterity"));
                    modifier.add(rs.getInt("Intelligence"));
                    modifier.add(rs.getInt("Constitution"));
                    modifier.add(rs.getInt("Luck"));
                }
                rs.close();
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return modifier;
    }

    public int newCharacter(ArrayList<String> data){
        int characterId=0;
        ArrayList<Integer> rc=getRaceModifiers(data.get(2));
        ArrayList<Integer> cl=getClassModifiers(data.get(1));
        ArrayList<Integer> finalPoints=FinalPoints(data, rc,cl);
        try {
            Class loadedClass = Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String connectionString = "jdbc:sqlite:races.db";
        try(Connection conn = DriverManager.getConnection(connectionString)) {
            String sql="Insert into Characters (Race_id, Classes_id, Name, Strength, Dexterity, Intelligence, Constitution, Luck, Experience) values (?,?,?,?,?,?,?,?,0)";
            try (PreparedStatement ps = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1,rc.get(0));
                ps.setInt(2,cl.get(0));
                ps.setString(3,data.get(0));
                ps.setInt(4,finalPoints.get(0));
                ps.setInt(5,finalPoints.get(1));
                ps.setInt(6,finalPoints.get(2));
                ps.setInt(7,finalPoints.get(3));
                ps.setInt(8,finalPoints.get(4));
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    characterId = rs.getInt(1);
                }
                rs.close();
            }
        }
        catch (SQLException ex) {
            ex.getErrorCode();
            ex.getSQLState();
            ex.getCause();
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    return characterId;
    }

    public int getCharId(String name){
        int characterId=0;
        try {
            Class loadedClass = Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String connectionString = "jdbc:sqlite:races.db";
        try(Connection conn = DriverManager.getConnection(connectionString)) {
            String sql = "SELECT Id from Characters where Name=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    characterId=rs.getInt("Id");
                }
            }
        }
        catch (SQLException ex) {
            ex.getErrorCode();
            ex.getSQLState();
            ex.getCause();
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return characterId;
    }

    public ArrayList<Integer> FinalPoints(ArrayList<String> data,ArrayList<Integer> rc,ArrayList<Integer> cl){
        ArrayList<Integer> finalPoints=new ArrayList<>();
        for (int i=3;i<data.size();i++){
            finalPoints.add((parseInt(data.get(i)))+rc.get(i-2)+cl.get(i-2));
        }
        return finalPoints;
    }


    public ArrayList<Character> getCharacters(){
        Character character=new Character();
        ArrayList<Character> characters=new ArrayList<>();
        try {
            Class loadedClass = Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String connectionString = "jdbc:sqlite:races.db";
        try(Connection conn = DriverManager.getConnection(connectionString)) {
            String sql="SELECT Characters.name as Name,Characters.Strength as Strength, Characters.Dexterity as Dexterity," +
                    " Characters.Constitution as Constitution, Characters.Intelligence as Intelligence, Characters.Luck as Luck, Characters.Experience as Experience, Characters.Armor as Armor," +
                    " Races.name as RaceName, Classes.name as ClassName from Characters inner join Races on Race_id=Races.Id inner join Classes on Classes_id=Classes.id";
            try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
                ResultSet rs  = pstmt.executeQuery();
                while(rs.next()) {
                    character=new Character();
                    character.setName(rs.getString("Name"));
                    character.setLevel(rs.getInt("Experience")/2000);
                    character.setRace(Character.Races.valueOf(rs.getString("RaceName")));
                    character.setCharacterClass(Character.Classes.valueOf(rs.getString("ClassName")));
                    character.setExperiencePoints(rs.getInt("Experience"));
                    character.setConstitution(rs.getInt("Constitution"));
                    character.setDexterity(rs.getInt("Dexterity"));
                    character.setIntelligence(rs.getInt("Intelligence"));
                    character.setLuck(rs.getInt("Luck"));
                    character.setStrength(rs.getInt("Strength"));
                    characters.add(character);
                }
                rs.close();
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return characters;
    }
    public Character getCharacter(int id){
        Character character=new Character();
        try {
            Class loadedClass = Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        String connectionString = "jdbc:sqlite:races.db";
        try(Connection conn = DriverManager.getConnection(connectionString)) {
            String sql="SELECT Characters.name as Name,Characters.Strength as Strength, Characters.Dexterity as Dexterity," +
                    " Characters.Constitution as Constitution, Characters.Intelligence as Intelligence, Characters.Luck as Luck, Characters.Experience as Experience, Characters.Armor as Armor," +
                    " Races.name as RaceName, Classes.name as ClassName from Characters inner join Races on Race_id=Races.Id inner join Classes on Classes_id=Classes.id where Characters.id=?";
            try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
                pstmt.setString(1,String.valueOf(id));
                ResultSet rs  = pstmt.executeQuery();
                while(rs.next()) {
                    character.setName(rs.getString("Name"));
                    character.setLevel(rs.getInt("Experience")/2000);
                    character.setRace(Character.Races.valueOf(rs.getString("RaceName")));
                    character.setCharacterClass(Character.Classes.valueOf(rs.getString("ClassName")));
                    character.setExperiencePoints(rs.getInt("Experience"));
                    character.setConstitution(rs.getInt("Constitution"));
                    character.setDexterity(rs.getInt("Dexterity"));
                    character.setIntelligence(rs.getInt("Intelligence"));
                    character.setLuck(rs.getInt("Luck"));
                    character.setStrength(rs.getInt("Strength"));
                }
                rs.close();
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return character;
    }
}


