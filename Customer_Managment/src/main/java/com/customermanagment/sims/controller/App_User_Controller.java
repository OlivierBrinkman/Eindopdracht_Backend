package com.customermanagment.sims.controller;
import com.customermanagment.sims.model.tables.app_user.App_User;
import com.customermanagment.sims.model.tables.app_user.App_User_Role;
import com.customermanagment.sims.model.tables.app_user.Roles;
import com.customermanagment.sims.service.app_user.App_User_Service_Implementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * App_User Controller
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
@Controller
public class App_User_Controller {

    final App_User_Service_Implementation appUserService;
    long selectedAppUserId = 0;long selectedAppUserRoleId = 0;

    /**
     * service constructor
     * @param appUserService
     */
    public App_User_Controller(App_User_Service_Implementation appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * returns Users
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String Users(Model model) {
        selectedAppUserId = 0;
        model.addAttribute("users",appUserService.getAppUsersForDisplay());
        return "app_user/App_Users";
    }

    /**
     * navigates to Create page
     * @param model
     * @return
     */
    @GetMapping("/users/new")
    public String newUser(Model model){
        model.addAttribute("newAppUser", new App_User());
        model.addAttribute("newAppUserRole", new App_User_Role());
        //model.addAttribute("roles", appUserService.getRoles());
        return "app_user/App_User_Create";
    }

    /**
     * creates App_User
     * @param appUser
     * @param appUserRole
     * @return
     */
    @PostMapping("/users/new")
    public String createUser(@ModelAttribute App_User appUser, @ModelAttribute App_User_Role appUserRole) {
        for(Roles role : Roles.values()) {
            if(appUserRole.getUserRole() == role.toString()) {
                appUserRole.setUserRole(role.toString());
            }
        }
        long userId  = appUserService.createAppUser(appUser);
        appUserRole.setUserId(userId);
        appUserService.createUserRole(appUserRole);
        return "redirect:/users";
    }

    /**
     * deletes App_User
     * @param userId
     * @return
     */
    @RequestMapping(value = "/users/delete" ,method = RequestMethod.GET)
    public String deleteUser(@RequestParam(name="appUserId")long userId) {
        try { appUserService.deleteUserRole(appUserService.getRoleByAppUserId(userId).getId());}
        catch(Exception e) { }
        appUserService.deleteAppUser(userId);
        return "redirect:/users";
    }

    /**
     * navigates to Update screen
     * @param appUserId
     * @param model
     * @return
     */
    @GetMapping(value = "/users/edit")
    public String selectedUser(@RequestParam(name="appUserId")long appUserId, Model model) {
        if(appUserId == 0) { return "redirect:/users"; }
        selectedAppUserId = appUserId;
        selectedAppUserRoleId = appUserService.getRoleByAppUserId(appUserId).getId();
        App_User appUserToEdit = appUserService.getAppUserById(appUserId);

        model.addAttribute("selectedAppUser", appUserToEdit);
        model.addAttribute("selectedAppUserRole", appUserService.getRoleByAppUserId(appUserId));

        System.out.println(appUserId);

        return "app_user/App_User_Update";
    }

    /**
     * updates App_User
     * @param appUser
     * @param appUserRole
     * @param result
     * @return
     */
    @PostMapping(value = "/users/edit/{appUserId}", consumes = "application/x-www-form-urlencoded")
    public String updateUser(App_User appUser, App_User_Role appUserRole, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/users";
        }
        appUser.setId(selectedAppUserId);
        appUserRole.setId(selectedAppUserRoleId);
        appUserRole.setUserId(selectedAppUserId);
        appUserService.createAppUser(appUser);
        appUserService.createUserRole(appUserRole);
        return "redirect:/users";
    }
}
