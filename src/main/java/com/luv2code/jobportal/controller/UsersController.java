package com.luv2code.jobportal.controller;

import com.luv2code.jobportal.services.UsersService;
import com.luv2code.jobportal.services.UsersTypeService;
import com.luv2code.jobportal.entity.UsersType;
import com.luv2code.jobportal.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;



@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes",usersTypes);
        model.addAttribute("user",new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users){
        usersService.addNew(users);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletRequest response){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,
                    (HttpServletResponse) response,authentication);
        }
        return "redirect:/";
    }
}
