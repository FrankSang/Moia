package com.franksang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by root on 1/26/16.
 */
public class SetHeadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //resp.setHeader("","");
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("default.png");
        OutputStream out = resp.getOutputStream();
        byte[] b= new byte[in.available()];

        in.read(b);
        out.write(b);
        in.close();
        out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        doGet(req, resp);
    }
}
