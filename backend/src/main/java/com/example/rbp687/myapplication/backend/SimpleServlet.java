package com.example.rbp687.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by RBP687 on 9/8/2016.
 */

public class SimpleServlet extends HttpServlet {
    static Logger Log = Logger.getLogger("com.example.rbp687.myapplication.backend.MyServlet");
    private static final Logger log = Logger.getLogger(MyEndpoint.class.getName());

    private String message;

    public void init() throws ServletException
    {
        // Do required initialization
        message = "Hello World";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");
        // Set response content type
        resp.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = resp.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPut");
        super.doPut(req, resp);
    }
}
