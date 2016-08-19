/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.Jirka.myapplication.backend;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringTransfer extends HttpServlet {
    String storedStr = "";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        //storedStr = "Ahoj";
        resp.setContentType("text/plain");      // nastavení typu obsahu - zde čistý text
        resp.getWriter().println("StoredText:" + storedStr);

        /*
        if (storedStr=="") {
            resp.getWriter().println("Zadny string zatim ulozen neni");   //getWriter z responderu a zápis věty
        } else {
            resp.getWriter().println(storedStr);
        }
        */
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String str = req.getParameter("string");    // extrakce parametru "name" z requestu
        resp.setContentType("text/plain");          // opět nastavení typu obdahu
        if(str == null) {
            storedStr = "";
            resp.getWriter().println("Missing string parameter"); // pokud je jméno prázdné poskytne návod
        } else {
            storedStr = str;
            resp.getWriter().println("OK Stored (string = " + str + ")");          // pokud ne, pozdraví
        }
    }
}
