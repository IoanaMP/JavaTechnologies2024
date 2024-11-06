/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import javax.annotation.Resource;

/**
 *
 * @author ioana
 */

public class DatabaseConnection {
//    private static final String URL = "jdbc:postgresql://localhost:5432/VehicleRoutingDatabase";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "admin";

//    static {
//       try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("nu merge");
//             throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }

    public Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PostgresDS");
        return ds.getConnection();
    }
}
