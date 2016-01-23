package com.franksang.service;

import com.franksang.dao.UserDao;
import com.franksang.dao.impl.UserDaoImpl;
import com.franksang.entity.User;
import com.franksang.utils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 1/23/16.
 */
public class LoginService {
    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public boolean checklogin(User user) {
        UserDao ud = new UserDaoImpl();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getInstance().getConnection();
            rs = ud.getUser(conn, user);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.getInstance().free(rs, null, conn);
        }
        return false;
    }
}
