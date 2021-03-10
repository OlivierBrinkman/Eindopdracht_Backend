package com.customermanagment.sims.service.app_user;
import com.customermanagment.sims.model.tables.app_user.App_User;
import com.customermanagment.sims.model.tables.app_user.App_User_Role;
import com.customermanagment.sims.model.structures.App_User_Structure;
import java.util.List;
/**
 * App_User Service
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public interface App_User_Service {

     App_User getAppUserById(long appUserId);
     App_User_Role getRoleByAppUserId(long appUserId);
     List<App_User> getAppUsers();
     List<App_User_Structure> getAppUsersForDisplay();

     void deleteAppUser(long userId);
     void createUserRole(App_User_Role userRole);
     void deleteUserRole(long userRoleId);

     long createAppUser(App_User appUser);
     String passwordEncoder(String password);

}
