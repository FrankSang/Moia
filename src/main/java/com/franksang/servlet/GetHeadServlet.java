package com.franksang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by root on 1/26/16.
 */
public class GetHeadServlet  extends HttpServlet {

    /**
     * Created by root on 1/26/16.
     */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //super.doGet(req, resp);
            HttpSession session = req.getSession();
            Enumeration enums = req.getHeaderNames();
            while (enums.hasMoreElements()) {
                String name = (String) enums.nextElement();
                String Value = req.getHeader(name);
                System.out.println(name + ":" + Value);
            }
        }
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //super.
            doPost(req, resp);
        }
    }

