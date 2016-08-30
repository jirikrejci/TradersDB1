package com.example.jirka.tradersdb1.NasServer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Jirka on 25.8.2016.
 */
public class NasFtp2 extends NasGeneral2 {

    public static String readStringFromFtpFile(String remoteFileName) {
        boolean success = false;

        FTPClient ftpClient = new FTPClient();
        try {
            //ftpClient.connect("192.168.0.10", 21);
            ftpClient.connect("188.175.124.99", 21);
            ftpClient.login("TraderDBUser", "traderpass");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

            //System.out.println(ftpClient.getStatus().toString());

            InputStream inputStream = ftpClient.retrieveFileStream(remoteFileName);

            String output_str = readStreamToString(inputStream);
            System.out.println("Downloaded file <" + remoteFileName + ">:");
            System.out.println(output_str);

            success = ftpClient.completePendingCommand();
            if (success) {
                System.out.println("File #2 has been downloaded successfully.");
            }

            inputStream.close();
            return output_str;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(remoteFileName);
            return "";
        }
    }


    public static void writeStringToFtp (String remoteFileName, String textToWrite) {
        boolean success = false;

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("192.168.0.10", 21);


            ftpClient.login("TraderDBUser", "traderpass");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

            //System.out.println(ftpClient.getStatus().toString());

            InputStream inputStream = ftpClient.retrieveFileStream(remoteFileName);
            OutputStream outputStream = ftpClient.storeFileStream(remoteFileName);

            writeStringToStream(outputStream, textToWrite);
            outputStream.flush();
            outputStream.close();

            success = ftpClient.completePendingCommand();
            if (success) {
                System.out.println("File #2 has been uploaded successfully.");
            }
            ftpClient.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(remoteFileName);
        }


    }


}
