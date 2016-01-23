package com.franksang.dao.impl;

import com.franksang.dao.UserDao;
import com.franksang.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 1/23/16.
 */
public class UserDaoImpl implements UserDao {

    @Override
    public ResultSet getUser(Connection conn, User user) throws SQLException {

        String LoginSQL="Select username,passwd from users where username=? and passwd=?";
        PreparedStatement ps =
                conn.prepareStatement(LoginSQL);
        //System.out.println("Select username,passwd from users where username=? and passwd=?;");
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassWord());
        ///**/ResultSet rs= ps .executeQuery();

        return ps.executeQuery();
    }
}
