package com.customermanagment.sims.model.structures;
import com.customermanagment.sims.model.tables.app_user.App_User;
import com.customermanagment.sims.model.tables.app_user.App_User_Role;

/**
 * App_User_Display Entity
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public class App_User_Structure {

    //Attributes
    public App_User appUser;
    public App_User_Role appUserRole;

    //Constructors
    public App_User_Structure(App_User appUser, App_User_Role appUserRole) {
        this.appUser = appUser;
        this.appUserRole = appUserRole;
    }
    public App_User_Structure() { }

    //Getters and Setters
    public App_User getAppUser() {
        return appUser;
    }
    public void setAppUser(App_User appUser) {
        this.appUser = appUser;
    }

    public App_User_Role getAppUserRole() {
        return appUserRole;
    }
    public void setAppUserRole(App_User_Role appUserRole) {
        this.appUserRole = appUserRole;
    }

    //To string
    @Override
    public String toString() {
        return "App_User_Display{" +
                "appUser=" + appUser +
                ", appUserRole=" + appUserRole +
                '}';
    }
}
