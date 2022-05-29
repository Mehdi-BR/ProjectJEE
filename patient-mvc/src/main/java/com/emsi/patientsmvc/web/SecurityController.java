package com.emsi.patientsmvc.web;

import com.emsi.patientsmvc.security.entities.AppUser;
import com.emsi.patientsmvc.security.services.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SecurityController {

    private SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }


    @GetMapping("/403")
    public String notAuthorized() {
        return "403";
    }

    public String getUserID(Principal principal) throws NullPointerException{
        String username=principal.getName();
        AppUser appUser=securityService.loadUserByUsername(username);
        String userID=appUser.getUserId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
return userID;
    }
}