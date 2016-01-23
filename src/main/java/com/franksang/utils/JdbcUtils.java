package com.franksang.utils;

import java.sql.*;

/**
 * Created by root on 11/29/15.
 */
public final class JdbcUtils {
    private String url = "jdbc:oracle:thin:ds/ds@192.168.83.30:1521/ORCL";
    //private String user = "sa";
    //private String password = "abcd-1234";
    private static JdbcUtils Instance = null;

    /*    public static synchronized JdbcUtilsSindle getInstance() {
            if (Instance == null)
                return new JdbcUtilsSindle();
            return Instance;
        }*/
    public static JdbcUtils getInstance() {
        if (Instance == null) {

            synchronized (JdbcUtils.class) {
                if (Instance == null) {
                    return new JdbcUtils();
                }
            }

        }
        return Instance;
    }

    private JdbcUtils() {
    }

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void free(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}