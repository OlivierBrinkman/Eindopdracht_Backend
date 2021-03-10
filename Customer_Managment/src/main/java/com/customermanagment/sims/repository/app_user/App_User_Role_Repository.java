package com.customermanagment.sims.repository.app_user;
import com.customermanagment.sims.model.tables.app_user.App_User_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * App_User_Role Repository
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Repository
public interface App_User_Role_Repository extends JpaRepository<App_User_Role, Long> {



}
