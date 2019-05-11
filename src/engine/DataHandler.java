package engine;

import javax.swing.*;
import java.io.*;

public class DataHandler {

        // A karakter lap elmentése, betöltése fájlból

        public void WriteData(Character character, File file)
        {
            try
            {
                FileOutputStream fileOut =
                        new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(character);
                out.close();
                fileOut.close();
                System.out.println("Serialized data is saved in ".concat(file.getCanonicalPath()));
            }catch(IOException i)
            {
                JOptionPane.showMessageDialog(null, i.getMessage());
            }
        }

        public Character ReadData(File file)
        {
            Character character;
            System.out.println("Reading data.");

            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                character = (Character) in.readObject();
                in.close();
                fileIn.close();
            } catch(IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage().concat("\nUsing default character."));
                return new Character();
            } catch(ClassNotFoundException c) {
                JOptionPane.showMessageDialog(null, c.getMessage().concat("\nUsing default character."));
                c.printStackTrace();
                return new Character();
            } catch (ClassCastException ce) {
                JOptionPane.showMessageDialog(null, ce.getMessage().concat("\nUsing default character."));
                return new Character();
            }

            return character;
        }
}