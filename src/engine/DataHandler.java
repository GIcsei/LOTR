package engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class DataHandler {

    private static final String MY_KEY = "character_id";
    private static final String MY_KEY2 = "Version_Number";
    public void Saver(int id) {
        Properties lastUsedCharacter = new Properties();
        lastUsedCharacter.setProperty(MY_KEY, (new String().valueOf(id)));
        System.out.println(lastUsedCharacter.getProperty(MY_KEY, "0"));
        lastUsedCharacter.setProperty(MY_KEY2, "0.9");
        try {
              lastUsedCharacter.store(new FileOutputStream("lastUsedCharacter.properties"), "Utoljára kimentett karakter Id-ja");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int Loader () {
        Properties lastUsedCharacter = new Properties();
        try {
            lastUsedCharacter.load(new FileInputStream("lastUsedCharacter.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id= Integer.parseInt(lastUsedCharacter.getProperty(MY_KEY));
        return id;
    }
    public String versionNumber(){
        Properties lastUsedCharacter = new Properties();
        try {
            lastUsedCharacter.load(new FileInputStream("lastUsedCharacter.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String versionNum= lastUsedCharacter.getProperty(MY_KEY2);
        return versionNum;
    }
}
