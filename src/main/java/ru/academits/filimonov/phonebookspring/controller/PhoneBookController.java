package ru.academits.filimonov.phonebookspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.academits.filimonov.phonebookspring.model.Contact;
import ru.academits.filimonov.phonebookspring.service.ContactService;
import ru.academits.filimonov.phonebookspring.service.ContactValidation;

import java.util.List;

@Controller
@RequestMapping("/phonebook")
public class PhoneBookController {
    static Logger log = LoggerFactory.getLogger(PhoneBookController.class);

    private ContactService phoneBookService;

    @Autowired
    public PhoneBookController(ContactService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addContact(@RequestBody Contact contact) {
        log.info("Adding new contact requested: " + contact);

        ContactValidation contactValidation = phoneBookService.addContact(contact);

        if (!contactValidation.isValid()) {
            log.error("Adding new contact error. Contact isn't valid: " + contactValidation.getError());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The contact isn't valid");
        }

        log.info("New contact added: " + contact);

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @RequestMapping("/get/all")
    @ResponseBody
    public List<Contact> getAllContacts() {
        log.info("The full contact list is requested");

        return phoneBookService.getAllContacts();
    }

    @RequestMapping("/get/filteredContacts")
    @ResponseBody
    public List<Contact> getFilteredContacts(@RequestParam String query) {
        log.info("Filtered contact list is requested with query: " + query);

        return phoneBookService.getFilteredContacts(query);
    }

    @RequestMapping("/deleteContact")
    @ResponseBody
    public ResponseEntity<String> deleteContact(@RequestParam String id) {
        log.info("The contact deletion is requested. Id: " + id);

        phoneBookService.deleteContactById(Integer.parseInt(id));

        log.info("Contact {id: " + id + "} is deleted");

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @RequestMapping("/deleteContacts")
    @ResponseBody
    public ResponseEntity<String> deleteContacts(@RequestParam String checkedRows) {
        log.info("The contacts deletion is requested. Ids: " + checkedRows);

        String[] idsStringArray = checkedRows.split(",");

        int[] idsArray = new int[idsStringArray.length];

        for (int i = 0; i < idsArray.length; i++) {
            idsArray[i] = Integer.parseInt(idsStringArray[i]);
        }

        phoneBookService.deleteContactsbyIds(idsArray);

        log.info("Contacts with ids " + checkedRows + " are deleted");

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
