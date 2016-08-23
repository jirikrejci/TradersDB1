/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.Jirka.myapplication.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringTransfer extends HttpServlet {
    String storedStr = "";

    /**
     * Called by the server (via the <code>service</code> method)
     * to allow a servlet to handle a POST request.
     * <p>
     * The HTTP POST method allows the client to send
     * data of unlimited length to the Web server a single time
     * and is useful when posting information such as
     * credit card numbers.
     * <p>
     * <p>When overriding this method, read the request data,
     * write the response headers, get the response's writer or output
     * stream object, and finally, write the response data. It's best
     * to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the
     * content type before accessing the <code>PrintWriter</code> object.
     * <p>
     * <p>The servlet container must write the headers before committing the
     * response, because in HTTP the headers must be sent before the
     * response body.
     * <p>
     * <p>Where possible, set the Content-Length header (with the
     * {@link ServletResponse#setContentLength} method),
     * to allow the servlet container to use a persistent connection
     * to return its response to the client, improving performance.
     * The content length is automatically set if the entire response fits
     * inside the response buffer.
     * <p>
     * <p>When using HTTP 1.1 chunked encoding (which means that the response
     * has a Transfer-Encoding header), do not set the Content-Length header.
     * <p>
     * <p>This method does not need to be either safe or idempotent.
     * Operations requested through POST can have side effects for
     * which the user can be held accountable, for example,
     * updating stored data or buying items online.
     * <p>
     * <p>If the HTTP POST request is incorrectly formatted,
     * <code>doPost</code> returns an HTTP "Bad Request" message.
     *
     * @param req  an {@link HttpServletRequest} object that
     *             contains the request the client has made
     *             of the servlet
     * @param resp an {@link HttpServletResponse} object that
     *             contains the response the servlet sends
     *             to the client
     * @throws IOException      if an input or output error is
     *                          detected when the servlet handles
     *                          the request
     * @throws ServletException if the request for the POST
     *                          could not be handled
     * @see ServletOutputStream
     * @see ServletResponse#setContentType
     */


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
