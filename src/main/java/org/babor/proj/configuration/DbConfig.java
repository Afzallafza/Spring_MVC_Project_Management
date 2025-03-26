package org.babor.proj.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    private DbConfig() {}
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String DB_URL=AppProperties.getInstance().getProperty("mysql.db.url");
        String DB_USER=AppProperties.getInstance().getProperty("mysql.db.user");
        String DB_PASSWORD=AppProperties.getInstance().getProperty("mysql.db.password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }
}
