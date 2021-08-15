package com.project.controller.admin;

import com.project.domain.GenericPagination;
import com.project.domain.PaginationResult;
import com.project.entity.User;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller(value = "userOfAdmin")
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenericPagination<User> genericPagination;

    @GetMapping("/user")
    public String getListUser(@RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                              @RequestParam(value = "limit", defaultValue = "2", required = false) Integer limit,
                              @RequestParam(value = "sortField", defaultValue = "userName", required = false) String sortField,
                              @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
                              @RequestParam(value = "keysearch", required = false) String keysearch,
                              Model model){

        Pageable pageable = PageRequest.of(pageNo - 1, limit,
                ("asc".equals(sortDir) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()));
        Page<User> page = null;

        if (model.asMap().get("search") != null || keysearch != null) {
            String search = model.asMap().get("search") != null ? model.asMap().get("search").toString() : keysearch.trim() ;
            model.addAttribute("search", search);

            page = userService.findByUserNameLike("%" + search + "%", pageable);
        }else{
            page = userService.findByIsEnable(true, pageable);
        }

        PaginationResult<User> paginationResult = genericPagination.pagination(page, pageNo, limit, sortField, sortDir);
        model.addAttribute("users", paginationResult);

        return "admin/user";
    }

    @PostMapping("/user/search")
    public String searchUser(@RequestParam String search, RedirectAttributes redirect) {
        redirect.addFlashAttribute("search", search);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "admin/edituser";
    }

    @PostMapping("/user")
    public String postUser(@ModelAttribute @Valid User user, BindingResult result){
        if (result.hasErrors()){
            return "redirect:/admin/user/"+user.getId()+"?invalid";
        }
        User u = userService.findById(user.getId());
        if (u != null){
            u.setFullName(user.getFullName());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setAddress(user.getAddress());
            u.setIsEnable(user.getIsEnable());
            userService.postUser(u);
            return "redirect:/admin/user/"+user.getId();
        }
        return "redirect:/admin/user/"+user.getId()+"?not-existed";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Integer id){
        userService.enableUser(false, id);
        return "redirect:/admin/user";
    }
}
