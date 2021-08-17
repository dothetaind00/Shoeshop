package com.project.controller.admin;

import com.project.entity.Contact;
import com.project.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller(value = "contactOfAdmin")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/admin/contact")
    public String getPage(){
        return "admin/contact";
    }

    @GetMapping("/admin/contact/{id}")
    public String getUpdateContact(@PathVariable Integer id, Model model) {
        Contact contact = contactService.findById(id).orElse(null);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "admin/editcontact";
        }
        return "redirect:/admin/contact";
    }

    @GetMapping("/api/contact/{pageNo}")
    @ResponseBody
    public Page<Contact> getAllContact(@PathVariable(value = "pageNo") Optional<Integer> pageNo){
        int page = pageNo.orElse(1);
        Pageable pageable = PageRequest.of(page - 1,8, Sort.by("name").ascending());
        Page<Contact> listContact = contactService.findAllContact(pageable);
        return listContact;
    }

    @GetMapping("/api/contact/{pageNo}/{name}")
    @ResponseBody
    public Page<Contact> getAllContactName(@PathVariable(value = "pageNo") Optional<Integer> pageNo,
                                            @PathVariable(value = "name") String name){
        int page = pageNo.orElse(1);
        Pageable pageable = PageRequest.of(page - 1,8, Sort.by("name").ascending());
        Page<Contact> listContact = contactService.findAllByName("%"+name+"%", pageable);
        return listContact;
    }

    @PutMapping("/api/contact/{id}")
    @ResponseBody
    public ResponseEntity<Contact> editContact(@PathVariable Integer id,@RequestBody Contact contact){
        Contact c = contactService.findById(id)
                            .orElseThrow(() -> new RuntimeException("Not found Contact with " + id));
        c.setName(contact.getName());
        c.setEmail(contact.getEmail());
        c.setPhone(contact.getPhone());
        c.setAddress(contact.getAddress());

        Contact updateContact = contactService.save(c);
        return ResponseEntity.ok(updateContact);
    }

    @DeleteMapping("/api/contact/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> deleteContact(@PathVariable Integer id){
        Contact contact = contactService.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found Contact with " + id));
        contactService.delete(contact);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
