package ru.academits.filimonov.phonebookspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.academits.filimonov.phonebookspring.PhoneBook;
import ru.academits.filimonov.phonebookspring.model.Contact;
import ru.academits.filimonov.phonebookspring.service.ContactService;
import ru.academits.filimonov.phonebookspring.service.ContactValidation;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phonebook")
public class PhoneBookController {
    private ContactService phoneBookService = PhoneBook.phoneBookService;

    @RequestMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addContact(@RequestBody Contact contact) {
        ContactValidation contactValidation = phoneBookService.addContact(contact);

        if (!contactValidation.isValid()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The contact isn't valid");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @RequestMapping("/get/all")
    @ResponseBody
    public List<Contact> getAllContacts() {
        return phoneBookService.getAllContacts();
    }

    @RequestMapping("/get/filteredContacts")
    @ResponseBody
    public List<Contact> getFilteredContacts(@RequestParam String query) {
        return phoneBookService.getFilteredContacts(query);
    }

    @RequestMapping("/deleteContact")
    @ResponseBody
    public ResponseEntity<String> deleteContact(@RequestParam String id) {
        phoneBookService.deleteContactById(Integer.parseInt(id));

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @RequestMapping("/deleteContacts")
    @ResponseBody
    public ResponseEntity<String> deleteContacts(@RequestParam String checkedRows) {
        String[] idsStringArray = checkedRows.split(",");

        int[] idsArray = new int[idsStringArray.length];

        for (int i = 0; i < idsArray.length; i++) {
            idsArray[i] = Integer.parseInt(idsStringArray[i]);
        }

        phoneBookService.deleteContactsbyIds(idsArray);

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
