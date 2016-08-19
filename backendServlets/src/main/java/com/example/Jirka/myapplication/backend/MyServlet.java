/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.Jirka.myapplication.backend;

import java.io.IOException;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");      // nastavení typu obsahu - zde čistý text
        resp.getWriter().println("Please use the form to POST to this url");   //getWriter z responderu a zápis věty
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");    // extrakce parametru "name" z requestu
        resp.setContentType("text/plain");          // opět nastavení typu obdahu
        if(name == null) {
            resp.getWriter().println("Please enter a name"); // pokud je jméno prázdné poskytne návod
        }
        resp.getWriter().println(name + ", hello from Traders DB, whish you a Great day!");          // pokud ne, pozdraví
    }
}
