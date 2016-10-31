package com.JKSoft.Networking.fms;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jirka on 25.8.2016.
 */
public class Web extends General {


    public static void readTextFileFromWeb(String strURL) {
        int response_code;
        try {
            URL url = new URL(strURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            response_code = con.getResponseCode();
            if (response_code != 200) {
                System.out.println("Wrong response code");
                return;
            }

            String output_str = readStreamToString(con.getInputStream());
            System.out.println(output_str);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
