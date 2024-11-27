/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author ioana
 */
public class LoginDAO {

    public static boolean validate(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        String url = "jdbc:postgresql://localhost:5432/Reviews";
        String usernameDB = "postgres";
        String passwordDB = "admin";

        try {
            con = DriverManager.getConnection(url, usernameDB, passwordDB);
            ps = con.prepareStatement("Select u.username, u.password from Users u where u.username = ? and u.password = ?");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error --> " + ex.getMessage());
            return false;
        }
//        finally {
//            DataConnect.close(con);
//        }
        return false;
    }

    public static boolean isAdmin(String user, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        String url = "jdbc:postgresql://localhost:5432/Reviews";
        String usernameDB = "postgres";
        String passwordDB = "admin";

        try {
            con = DriverManager.getConnection(url, usernameDB, passwordDB);
            ps = con.prepareStatement("Select role from users where username = ? and password = ?");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                boolean isAdmin = "admin".equals(role);
                if (isAdmin) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        }
        return false;
    }
}
