package com.Modul2Pokus;

public class PokusModul2Class1 {
    public static void main (String [] args ) {

        System.out.println("Ahoj z Pokusn�ho modulu, kter� bude smaz�n, \nAhoj" );
        System.out.println(args[1].toString());
        System.out.println(args.length);

        for (int i = 0; i < args.length; i++ ) {
            System.out.println(args[i]);
        }

    }
}
