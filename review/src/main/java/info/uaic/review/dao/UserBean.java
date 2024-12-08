/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import info.uaic.review.entities.RoleEntity;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.repositories.RoleRepository;
import info.uaic.review.repositories.UserRepository;
import java.util.Collections;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
    
    @Inject
    private RoleRepository roleRepository;
    
    @Inject
    private EntityManager em;

    @Transactional
    public String register() {
        try {

         RoleEntity roleEntity = roleRepository.findByName(role);

        if (roleEntity == null) {

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Role not found: " + role));
            return "register.xhtml?faces-redirect=true";
        }
        
        if (!em.contains(roleEntity)) {
            roleEntity = em.merge(roleEntity);
        }
            UserEntity newUser = new UserEntity();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setPassword(password);
            
            // Assigning role to the user
            newUser.setRoles(Collections.singletonList(roleEntity));
            
            userRepository.save(newUser);

            switch (role.toLowerCase()) {
            case "admin":
                return "admin-dashboard.xhtml?faces-redirect=true";
            case "teacher":
                return "teacher-dashboard.xhtml?faces-redirect=true";
            case "student":
                return "evaluation.xhtml?faces-redirect=true";
            default:
                return "register.xhtml?faces-redirect=true";
             }
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