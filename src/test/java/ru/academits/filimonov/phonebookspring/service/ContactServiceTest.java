package ru.academits.filimonov.phonebookspring.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.academits.filimonov.phonebookspring.model.Contact;

import java.util.List;

@SpringBootTest
class ContactServiceTest {
    private ContactService contactService ;
    int contactsCount = 5;

    @Autowired
    public ContactServiceTest(ContactService contactService) {
        this.contactService = contactService;
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < contactsCount; i++) {
            Contact newContact = new Contact();

            newContact.setFirstName("FirstName" + i);
            newContact.setLastName("LastName" + i);
            newContact.setPhone("Phone" + i);

            contactService.addContact(newContact);
        }
    }

    @AfterEach
    void tearDown() {
        List<Contact> list = contactService.getAllContacts();

        for (Contact contact : list) {
            contactService.deleteContactById(contact.getId());
        }
    }

    @Test
    void validateContact() {
        Contact emptyContact = new Contact();

        emptyContact.setFirstName("");
        emptyContact.setLastName("");
        emptyContact.setPhone("");

        ContactValidation contactValidation = contactService.addContact(emptyContact);

        Assertions.assertFalse(contactValidation.isValid());
    }

    @Test
    void addContact() {
        Assertions.assertEquals(contactsCount, contactService.getAllContacts().size());
    }

    @Test
    void deleteContactById() {
        int id = contactService.getAllContacts().get(1).getId();

        contactService.deleteContactById(id);

        Assertions.assertEquals(contactsCount - 1, contactService.getAllContacts().size());
    }

    @Test
    void deleteContactsByIds() {
        int firstId = contactService.getAllContacts().get(1).getId();
        int secondId = contactService.getAllContacts().get(2).getId();

        contactService.deleteContactsbyIds(new int[]{firstId, secondId});

        Assertions.assertEquals(contactsCount - 2, contactService.getAllContacts().size());
    }

    @Test
    void deleteRandomContact() {
        System.out.println(contactService.getAllContacts().size());

        contactService.deleteRandomContact();

        System.out.println(contactService.getAllContacts().size());

        Assertions.assertEquals(contactsCount - 1, contactService.getAllContacts().size());
    }

    @Test
    void getAllContacts() {
        int gotContactsListSize = contactService.getAllContacts().size();

        Assertions.assertEquals(contactsCount, gotContactsListSize);
    }

    @Test
    void getFilteredContacts() {
        Assertions.assertEquals(1, contactService.getFilteredContacts("FirstName3").size());
    }
}