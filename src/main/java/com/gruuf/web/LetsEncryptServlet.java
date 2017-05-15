package com.gruuf.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LetsEncryptServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log("Handling LetsEncrypt request");
        String challenge = getInitParameter("challenge");

        response.setContentType("text/plain");
        response.getOutputStream().println(challenge);
    }
}
