package com.franksang.dbtest;

import com.franksang.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by root on 1/23/16.
 */
public class DBConTest {
    public static void main(String[] args)throws SQLException {
        Connection conn = JdbcUtils.getInstance().getConnection();
        System.out.println(conn==null);

    }
}
