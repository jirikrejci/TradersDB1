package com.JKSoft.Networking.tests;//import com.JKSoft.Networking.NetworkFms.*;


import com.JKSoft.Networking.fms.Ftp;


/**
 * Created by Jirka on 15.9.2016.
 */
public class TestsNetworkBasic {
    public static void main (String [] args ) {
        String inputStr ="not_filled";

        System.out.println("Test.main com.JKSoft.Networking, projekt Networking JK");
        System.out.println("-------------------------------------------------------");

        HealthCheck.sayHello();

//        Web.readTextFileFromWeb("http://www.lukaspetrik.cz/filemanager/tmp/reader/data.xml");  // OK
 //       FileSystem.readTextFileFromWinFs("c:/DevFiles/RelevantTrades.json");   // OK
//        FileSystem.writeTextToFileWinFs("c:/DevFiles/WrittenFile.txt", "Aho jirko, tohle je zapsaný text"); //OK

//          inputStr = FileSystem.readTextFileFromWinFs("R:/RelevantTrades.json"); System.out.println(inputStr);// OK

          inputStr = Ftp.readStringFromFtp("/FilesDB/RelevantTrades.json"); System.out.println(inputStr); // OK
 //
           Ftp.writeStringToFtp("/FilesDB/WrittenTextFile.txt", "Ahoj, tohle je soubor zapsaný přes FTP"); // OK


        //FileSystem.readTextFileFromFileSystem2("NASSERVER1\\volume1\\FilesDB\\RelevantTrades.json");  // not working yet


    System.out.println("Konec Testů");}
}
