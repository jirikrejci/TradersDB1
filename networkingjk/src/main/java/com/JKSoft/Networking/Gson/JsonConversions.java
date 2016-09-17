package com.JKSoft.Networking.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Jirka on 30.8.2016.
 */
public class JsonConversions {
    public static String getJsonPretty (Object object) {
        GsonBuilder builder = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls();


        Gson gson =  builder.create();
        return gson.toJson(object, object.getClass());

    }

}
