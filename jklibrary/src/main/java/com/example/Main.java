package com.example;


import com.JKSoft.NetworkLibrary.NetWorkLibrarySharedClass;

/**
 * Created by Jirka on 9.9.2016.
 */
public class Main {
    public static void main (String [] args) {
        System.out.println("Toto je main v pokusne Android JKLibrary");
        MyFirstAsLibraryClass.show();
        MyFirstAsLibraryClass.sayHello("Jirko jak se máš?");
        NetWorkLibrarySharedClass.show();
    }

}
