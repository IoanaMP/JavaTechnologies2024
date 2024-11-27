/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 *
 * @author ioana
 */
@SessionScoped
@Named
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "UserBean.findAll", query = "SELECT u FROM UserBean u"),
    @NamedQuery(name = "UserBean.findById", query = "SELECT u FROM UserBean u WHERE u.id = :id"),
    @NamedQuery(name = "UserBean.findByName", query = "SELECT u FROM UserBean u WHERE u.name = :name"),
    @NamedQuery(name = "UserBean.findByUsername", query = "SELECT u FROM UserBean u WHERE u.username = :username"),
    @NamedQuery(name = "UserBean.findByPassword", query = "SELECT u FROM UserBean u WHERE u.password = :password"),
    @NamedQuery(name = "UserBean.findByRole", query = "SELECT u FROM UserBean u WHERE u.role = :role"),
    @NamedQuery(name = "UserBean.countUsers", query = "SELECT COUNT(u) FROM UserBean u")})
public class UserEnntity implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "role")
    private String role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Evaluation> evaluationCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    public UserEnntity() {
    }

    public UserEnntity(Integer id) {
        this.id = id;
    }

    public UserEnntity(Integer id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEnntity)) {
            return false;
        }
        UserEnntity other = (UserEnntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.UserBean[ id=" + id + " ]";
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

    public Collection<Evaluation> getDocumentCollection() {
        return evaluationCollection;
    }

    public void setDocumentCollection(Collection<Evaluation> evaluationCollection) {
        this.evaluationCollection = evaluationCollection;
    }

}