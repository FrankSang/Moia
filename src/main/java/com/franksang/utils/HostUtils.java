package com.franksang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by root on 1/23/16.
 */
public class HostUtils {
    private static Logger logger = LoggerFactory.getLogger(HostUtils.class);
    private static String CONFIG = "config.properties";
    private static String path = HostUtils.class.getClassLoader().getResource("").getPath();

    public static String getOracleUrl() {

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(path + CONFIG));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dbdriver = prop.getProperty("db.driver");
        String dbtype = prop.getProperty("db.type");
        String dbuser = prop.getProperty("db.username");
        String dbpassword = prop.getProperty("db.password");
        String dbhost = prop.getProperty("db.host");
        String dbport = prop.getProperty("db.port");
        String dbname = prop.getProperty("db.name");
        StringBuilder sb = new StringBuilder();
        //"jdbc:oracle:thin:ds/ds@192.168.83.30:1521/ORCL";
        if ("Oracle".equalsIgnoreCase("oracle")) {
            sb.append("jdbc:oracle:thin:").append(dbuser).append("/").append(dbpassword)
                    .append("@").append(dbhost).append(":").append(dbport).append("/").append(dbname);
            //System.out.println(sb.toString());
            return sb.toString();
        } else {
            return null;
        }
    }
}
