package com.customermanagment.sims.repository.app_user;
import com.customermanagment.sims.model.tables.app_user.App_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * App_User Repository
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Repository
public interface App_User_Repository extends JpaRepository<App_User, Long> {

        App_User findApp_UserById(Long appUserId);
        App_User findApp_UserByUsernameAndPassword(String username, String password);
        List<App_User> getApp_UsersBy();
        void deleteApp_UserById(Long appUserId);

}