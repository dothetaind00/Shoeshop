package com.project.controller.admin;

import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.ResourceExhaustedException;
import com.project.entity.Contact;
import com.project.service.ContactService;
import com.project.domain.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller(value = "contactOfAdmin")
@RequestMapping("admin/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public String getContact(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageNo,
                             @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
                             @RequestParam(value = "sortField", defaultValue = "name", required = false) String sortField,
                             @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
                             Model model) {
        Pageable pageable = PageRequest.of(pageNo - 1, limit, ("asc".equals(sortDir) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending()));
        Page<Contact> page = contactService.findAllPaging(pageable);

        PaginationResult<Contact> pagination = new PaginationResult<>();
        pagination.setPageNo(pageNo);
        pagination.setLimit(limit);
        pagination.setTotalPage(page.getTotalPages());
        pagination.setTotalItem(page.getTotalElements());
        pagination.setSortField(sortField);
        pagination.setSortDir(sortDir);
        pagination.setList(page.toList());

        model.addAttribute("contacts", pagination);
        return "admin/contact";
    }

    @GetMapping("/{id}")
    public String getUpdateContact(@PathVariable Integer id, Model model) {
        Contact contact = contactService.findById(id).orElse(null);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "admin/editcontact";
        }
        return "redirect:/admin/contact";
    }

    @PostMapping
    public String updateContact(@ModelAttribute @Valid Contact contact, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin/contact/" + contact.getId() + "?invalid";
        }

        if (contactService.save(contact) == null)
            return "redirect:/admin/contact/" + contact.getId() + "?existed";

        return "redirect:/admin/contact/" + contact.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Integer id) {
        contactService.delete(id);
        return "redirect:/admin/contact";
    }

    @GetMapping("/page-contact")
    public String getPage(Model model){
        model.addAttribute("totalItem", contactService.totalRecord());
        return "admin/test_contact";
    }

    @GetMapping("/api-getall/{pageNo}")
    @ResponseBody
    public Page<Contact> getAllContact(@PathVariable(value = "pageNo") Optional<Integer> pageNo){
        int page = pageNo.orElse(1);
        Pageable pageable = PageRequest.of(page - 1,5, Sort.by("name").ascending());
        Page<Contact> listContact = contactService.findAllContact(pageable);
        return listContact;
    }

    @GetMapping("/api-getname/{pageNo}/{name}")
    @ResponseBody
    public Page<Contact> getAllContactName(@PathVariable(value = "pageNo") Optional<Integer> pageNo,
                                            @PathVariable(value = "name") String name){
        int page = pageNo.orElse(1);
        Pageable pageable = PageRequest.of(page - 1,5, Sort.by("name").ascending());
        Page<Contact> listContact = contactService.findAllByName("%"+name+"%", pageable);
        return listContact;
    }

    @GetMapping("/api-getentity/{id}")
    @ResponseBody
    public ResponseEntity<Contact> getContactById(@PathVariable Integer id){
        Contact contact = contactService.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found contact with id = "+id));
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/api-editcontact/{id}")
    @ResponseBody
    public ResponseEntity<Contact> editContact(@PathVariable Integer id,@RequestBody Contact contact){
        Contact contac = contactService.findById(id)
                            .orElseThrow(() -> new RuntimeException("Not found Contact with " + id));
        contac.setName(contact.getName());
        contac.setEmail(contact.getEmail());
        contac.setPhone(contact.getPhone());
        contac.setAddress(contact.getAddress());

        Contact updateContact = contactService.save(contac);
        return ResponseEntity.ok(updateContact);
    }

}
