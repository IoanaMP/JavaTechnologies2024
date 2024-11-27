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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author ioana
 */
@Named
@ApplicationScoped
public class LoginDAO {

    @Inject
    private EntityManager em;

    public String getUserRole(String username, String password) {
        try {
            String query = "SELECT r.name " +
                           "FROM UserEntity u " +
                           "JOIN u.roles r " +
                           "WHERE u.username = :username AND u.password = :password";

            return em.createQuery(query, String.class)
                     .setParameter("username", username)
                     .setParameter("password", password)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
