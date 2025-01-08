/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;
import info.uaic.review.dao.LoginBean;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.logging.LoggingInterceptor;
import jakarta.annotation.security.PermitAll;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
/**
 *
 * @author ioana
 */
@Transactional(rollbackOn = {SQLException.class})
@Named
@PermitAll
public class UserRepository {

    @Inject
    private EntityManager em;

    @Interceptors(LoggingInterceptor.class)
    @Transactional
    public void save(UserEntity user) {
        em.persist(user);
    }
    
    public UserEntity findTeacherById(Integer teacherId) {
        try {
            return em.createQuery(
                    "SELECT u FROM UserEntity u JOIN u.roles r WHERE u.id = :teacherId AND r.name = :roleName",
                    UserEntity.class)
                    .setParameter("teacherId", teacherId)
                    .setParameter("roleName", "teacher")
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserEntity> findAllTeachers() {
        return em.createQuery(
                "SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName",
                UserEntity.class)
                .setParameter("roleName", "teacher")
                .getResultList();
    }
    
    public UserEntity findById(String id) {
        try {
            return em.createNamedQuery("UserEntity.findById", UserEntity.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public UserEntity findByEmail(String username) {
        try {
            return em.createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                     .setParameter("email", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public String findUserRole(String username, String password) {
        try {
            return (String) em.createNativeQuery(
                    "SELECT r.id " +
                    "FROM users u " +
                    "JOIN user_roles ur ON ur.user_id = u.id " +
                    "JOIN roles r ON ur.role_id = r.id " +
                    "WHERE u.id = ? AND u.password = ?")
                    .setParameter(1, username)
                    .setParameter(2, password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
        
    public void checkUser(LoginBean loginBean) throws ServletException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        System.out.println("///////// "+ loginBean.getUsername()+" ///////////// " + loginBean.getPassword()+" /////////////////////////");
        String role = findUserRole(loginBean.getUsername(),loginBean.getPassword());
        System.out.println("///////// "+ role+" /////////////////////////");
        request.login(loginBean.getUsername(), loginBean.getPassword());
        System.out.println("Login successful!");
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        if (Objects.equals(role, "student")) {

            Cookie cookie = new Cookie("username", loginBean.getUsername());
            cookie.setMaxAge(1800);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect(context.getExternalContext().getRequestContextPath() + "/evaluation.xhtml");
        }else if(Objects.equals(role, "teacher"))
        {
            Cookie cookie = new Cookie("username", loginBean.getUsername());
            cookie.setMaxAge(1800);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect(context.getExternalContext().getRequestContextPath() + "/teacher-dashboard.xhtml");
        }
        else if (Objects.equals(role, "admin")) {
            Cookie cookie = new Cookie("username", loginBean.getUsername());
            cookie.setMaxAge(1800);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect(context.getExternalContext().getRequestContextPath() + "/admin-dashboard.xhtml");
        } else {
            response.sendRedirect(context.getExternalContext().getRequestContextPath() + "/error.xhtml");
        }
    }
}