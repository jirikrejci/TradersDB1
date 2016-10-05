package com.JKSoft.TdbClient.Convertors;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Jirka on 19.8.2016.
 */
public class HttpConvertor {
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
