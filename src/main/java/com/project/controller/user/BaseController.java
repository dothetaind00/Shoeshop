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

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SendMail sendMail;

    @GetMapping("/login")
    public String getPageLogin(){
        return "user/login";
    }

    @GetMapping("/register")
    public String getPageRegister(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/create-user")
    public String doRegister(@ModelAttribute @Valid User user, BindingResult result){
        if (result.hasErrors()){
            return "redirect:/register?invalid";
        }

        User u = userService.save(user);
        if (u == null)
            return "redirect:/register?existed";

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = "<a href=\"http://localhost:8080/actived/" + u.getToken() +
                            "\">Actice account !</a>";
                    sendMail.confirmMail(u.getEmail(),url);
                    Thread.sleep(10*60*1000);
                    if (userService.existUserByTokenAndIsEnable(u.getToken(), false)){
                        userService.delete(u.getId());
                    }
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        });
        thread.start();

        return "redirect:/login";
    }

    @GetMapping("/actived/{token}")
    public String activeUser(@PathVariable(value = "token") String token){
        User user = userService.findUserByTokenAndIsEnable(token, false).orElse(null);
        if (user != null){
            //add role for user
            Role role = roleService.findRoleByRole("USER");
            user.addRole(role);
            user.setIsEnable(true);
            userService.save(user);
            return "redirect:/login?actived";
        }
        return "redirect:/login?expiredToken";
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
