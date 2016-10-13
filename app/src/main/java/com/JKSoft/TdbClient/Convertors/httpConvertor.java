package com.JKSoft.TdbClient.convertors;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Jirka on 19.8.2016.
 */
public class HttpConvertor {

    /**
     * Extracts and converts HTTP response body to String
     * @param response - HTTP response object
     * @return - string representig body
     */

    public static String responseBodyToString (Response response) {
        String result = "";

        //Try to get response body
        if (response.getBody() instanceof TypedByteArray) {
            TypedByteArray b = (TypedByteArray) response.getBody();
            result = new String(b.getBytes());
        }
        return result;
    }
}
