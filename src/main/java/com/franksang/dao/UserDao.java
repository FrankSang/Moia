package com.franksang.dao;

import com.franksang.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 1/23/16.
 */
public interface UserDao {
    public ResultSet getUser(Connection conn, User user) throws SQLException;
}
