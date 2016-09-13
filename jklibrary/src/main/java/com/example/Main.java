package com.example;


import com.JKSoft.NetworkLibrary.NetWorkLibrarySharedClass;

/**
 * Created by Jirka on 9.9.2016.
 */
public class Main {
    public static void main (String [] args) {
        System.out.println("Toto je main v pokusne Android JKLibrary");
        MyFirstLibraryClass.show();
        MyFirstLibraryClass.sayHello("Jirko jak se máš?");
        NetWorkLibrarySharedClass.show();
    }

}
