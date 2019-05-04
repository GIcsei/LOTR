package main;

import Races.*;

public class Main {
    public static void main(String[] args) {
        Woodman_character woody=new Woodman_character("Woody",20,30,30,30,30,30,30);
        woody.printAllTraits();
        Dwarf_character dwarfy=new Dwarf_character("Dwarfy",20,30,30,30,30,30,30);
        dwarfy.printAllTraits();
    }
}
