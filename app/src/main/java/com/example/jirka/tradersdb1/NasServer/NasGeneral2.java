package com.example.jirka.tradersdb1.NasServer;

import java.io.*;

/**
 * Created by Jirka on 25.8.2016.
 */
public class NasGeneral2 {
    protected static String readStreamToString(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        {
            StringBuffer strBuffer = new StringBuffer(1024);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    strBuffer.append(line + "\n");
                }
                return strBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

        }
    }

    protected  static void writeStringToStream (OutputStream outputStream, String stringToWrite) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(stringToWrite);
        printWriter.flush();
        printWriter.close();
    }


}
