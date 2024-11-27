/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.entities;

import info.uaic.review.dao.LoginDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author ioana
 */
@SessionScoped
@Named
public class Login implements Serializable {

    @Inject
    private LoginDAO loginDAO;
    
    private String password;
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String validateUsernamePassword() {
            String role = loginDAO.getUserRole(username, password);

            if (role == null) {
                FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username or Password",
                            "Please enter the correct credentials"));
                return "login";
            }
// de pus in session poate?
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", role);
            return role.toLowerCase();
    }

}
