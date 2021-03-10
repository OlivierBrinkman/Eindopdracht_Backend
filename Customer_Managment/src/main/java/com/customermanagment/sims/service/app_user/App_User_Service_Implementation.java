package com.customermanagment.sims.service.app_user;
import com.customermanagment.sims.Web_Security_Config;
import com.customermanagment.sims.model.tables.app_user.App_User;
import com.customermanagment.sims.model.tables.app_user.App_User_Role;
import com.customermanagment.sims.model.structures.App_User_Structure;
import com.customermanagment.sims.repository.app_user.App_User_Repository;
import com.customermanagment.sims.repository.app_user.App_User_Role_Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * App_User Service implementation.
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Service
public class App_User_Service_Implementation implements App_User_Service {

    final App_User_Repository appUserRepository;
    final App_User_Role_Repository appUserRoleRepository;

    /**
     * service constructor
     * @param appUserRepository
     * @param appUserRoleRepository
     */
    public App_User_Service_Implementation(App_User_Repository appUserRepository, App_User_Role_Repository appUserRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserRoleRepository = appUserRoleRepository;
    }

    /**
     * get all app users
     * @return
     */
    @Override
    public List<App_User> getAppUsers() {
        return appUserRepository.findAll();
    }

    /**
     * get app user by id
     * @param appUserId
     * @return
     */
    @Override
    public App_User getAppUserById(long appUserId) {
        return appUserRepository.findById(appUserId).get();
    }

    /**
     * get role by app user id
     * @param appUserId
     * @return
     */
    @Override
    public App_User_Role getRoleByAppUserId(long appUserId) {
        App_User_Role local_AppUserRole = new App_User_Role();
        for (App_User_Role appUserRole : appUserRoleRepository.findAll()) {
            if (appUserRole.getUserId() == appUserId) {
                local_AppUserRole = appUserRole;
            }
        }
        return local_AppUserRole;
    }

    /**
     * get app users in structure for display
     * @return
     */
    @Override
    public List<App_User_Structure> getAppUsersForDisplay() {
        List<App_User_Structure> appUserDisplayList = new ArrayList<>();
        for (App_User appUser : appUserRepository.findAll()) {
            appUser.setPassword(passwordEncoder(appUser.getPassword()));
            App_User_Structure tempAppUserDisplay = new App_User_Structure(appUser, getRoleByAppUserId(appUser.getId()));
            appUserDisplayList.add(tempAppUserDisplay);
        }
        return appUserDisplayList;
    }

    /**
     * creates app user
     * @param appUser
     * @return
     */
    @Override
    public long createAppUser(App_User appUser) {
        return appUserRepository.save(appUser).getId();
    }

    /**
     * creates user role
     * @param userRole
     */
    @Override
    public void createUserRole(App_User_Role userRole) {
        appUserRoleRepository.save(userRole);
    }

    /**
     * deletes app user by id
     * @param userId
     */
    @Override
    public void deleteAppUser(long userId) {
        appUserRepository.deleteById(userId);
    }

    /**
     * delets user role by id
     * @param userRoleId
     */
    @Override
    public void deleteUserRole(long userRoleId) {
        appUserRoleRepository.deleteById(userRoleId);
    }

    /**
     * password encoder
     * @param password
     * @return
     */
    @Override
    public String passwordEncoder(String password) {
        Web_Security_Config webConfig = new Web_Security_Config();
        String encodedPassword = webConfig.passwordEncoder().encode(password);
        return encodedPassword;
    }

}