package com.franksang.servlet;

import com.franksang.entity.User;
import com.franksang.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 1/23/16.
 */
//@WebServlet("LoginServlet")
public class LoginServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username + "======" + password);
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        boolean login = new LoginService().checklogin(user);
        String forward = null;

        if (login) {
            forward = "sucess.jsp";
        } else {
            forward = "faile.jsp";
        }
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }
}
