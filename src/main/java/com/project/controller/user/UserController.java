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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SendMail sendMail;

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model){
        User user = userService.findUserByUserNameAndIsEnable(username,true);
        if (user == null){
            return "redirect:/user/"+username+"?not-existed";
        }
        model.addAttribute("user", user);
        return "user/edit-user";
    }

    @PostMapping
    public String saveUser(@ModelAttribute @Valid User user, BindingResult result){
        if (user.getId() == null){
            //create
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
                        String url = "<a href=\"http://localhost:8080/user/actived/" + u.getToken() +
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

        }else{
            //update
            if (result.hasErrors()){
                return "redirect:/user/"+user.getUserName()+"?invalid";
            }
            userService.save(user);
            return "redirect:/user/"+user.getUserName();
        }
    }

    @GetMapping("/actived/{token}")
    public String activeUser(@PathVariable(value = "token") String token){
        User user = userService.findUserByTokenAndIsEnable(token, false).orElse(null);
        if (user != null){
            Role role = roleService.findRoleByRole("USER");
            user.addRole(role);
            user.setIsEnable(true);
            userService.activeUser(user);
            return "redirect:/login?actived";
        }
        return "redirect:/login?expiredToken";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(){
        return "";
    }
}
