package com.project.controller.admin;

import com.project.entity.Contact;
import com.project.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller(value = "contactOfAdmin")
@RequestMapping("admin/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("")
    public String getContact(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "admin/contact";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateContact(@PathVariable Integer id, Model model) {
        Contact contact = contactService.findById(id);
        model.addAttribute("contact", contact);
        return "admin/editcontact";
    }

    @PostMapping("/update")
    public String updateContact(@ModelAttribute @Valid Contact contact, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin/contact/edit/{" + contact.getId() + "}?invalid";
        }
        contactService.save(contact);
        return "redirect:/admin/contact/edit/" + contact.getId();
    }
}
