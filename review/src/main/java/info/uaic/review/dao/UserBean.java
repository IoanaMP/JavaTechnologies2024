/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import info.uaic.review.entities.RoleEntity;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.repositories.UserRepository;
import java.util.Collections;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ioana
 */
@Named
@RequestScoped
public class UserBean {

    private String name;
    private String username;
    private String password;
    private String role;

    @Inject
    private UserRepository userRepository;

    public String register() {
        try {
            UserEntity newUser = new UserEntity();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setPassword(password);
            
            // Assigning role to the user
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName(role);
            newUser.setRoles(Collections.singletonList(roleEntity));

            userRepository.save(newUser);

            return "success.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return ""; //error page sth like error.xhtml?faces-redirect=true
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}