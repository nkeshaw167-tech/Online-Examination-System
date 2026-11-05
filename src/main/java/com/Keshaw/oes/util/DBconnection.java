package com.Keshaw.oes.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnection {

    public static Connection getConnection()
            throws SQLException, IOException {

        Properties properties = new Properties();

        try (InputStream input =
                     DBconnection.class.getClassLoader()
                             .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new IOException("db.properties file not found");
            }

            properties.load(input);
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, username, password);
    }
}
