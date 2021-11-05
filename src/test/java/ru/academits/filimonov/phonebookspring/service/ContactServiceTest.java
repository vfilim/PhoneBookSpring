package ru.academits.filimonov.phonebookspring.service;

import org.junit.jupiter.api.*;
import ru.academits.filimonov.phonebookspring.model.Contact;

class ContactServiceTest {
    private static ContactService contactService = new ContactService();
    int contactsCount = 5;

    @BeforeEach
    void setUp() {
        contactService.deleteContactById(1);

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
        ContactService contactService = new ContactService();
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
        contactService.deleteRandomContact();

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