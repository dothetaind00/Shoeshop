package com.project.controller.user;

import com.project.entity.Role;
import com.project.entity.User;
import com.project.service.RoleService;
import com.project.service.UserService;
import com.project.service.sendmail.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BaseController {

    @GetMapping("/login")
    public String getPageLogin(){
        return "user/login";
    }

    @GetMapping("/register")
    public String getPageRegister(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @GetMapping("/contact")
    public String getContact(){
        return "redirect:/user/contact";
    }

    @GetMapping("/403")
    public String accessDenied(){
        return "403";
    }

}
