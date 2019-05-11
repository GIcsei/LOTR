package database;

import java.sql.*;

public class Modifiers {
    public
    int getStrengthModifier(int classid)
        {
        int strengthmodifier = 0;
            try {
                Class loadedClass = Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        String connectionString = "jdbc:sqlite:races.db";
        try(Connection conn = DriverManager.getConnection(connectionString)) {

            try (Statement stmt = conn.createStatement()) {
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Races_modifiers");
                while(rs.next()) {
                    if(rs.getInt("Races_id")==classid){strengthmodifier=rs.getInt("strength");}
                }
                rs.close();
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return strengthmodifier;
    }
}