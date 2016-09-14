package com.Modul2Pokus;

public class PokusModul2Class1 {
    public static void main (String [] args ) {

        System.out.println("Ahoj z Pokusného modulu, který bude smazán, \nAhoj" );
        System.out.println(args[1].toString());
        System.out.println(args.length);

        for (int i = 0; i < args.length; i++ ) {
            System.out.println(args[i]);
        }

    }
}
