/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;


import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author ioana
 */
@Named("localeBean")
@SessionScoped
public class LocaleBean implements Serializable{

    private Locale locale;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
        } else {
            locale = Locale.ENGLISH;
            System.out.println("FacesContext was null; defaulting to English");
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale != null ? locale.getLanguage() : "en";
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
