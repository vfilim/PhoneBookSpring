package ru.academits.filimonov.phonebookspring.service;

import ru.academits.filimonov.phonebookspring.PhoneBook;
import ru.academits.filimonov.phonebookspring.dao.ContactDao;
import ru.academits.filimonov.phonebookspring.model.Contact;

import java.util.List;
import java.util.stream.Collectors;


public class ContactService {
    private ContactDao contactDao = PhoneBook.contactDao;

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.getAllContacts();
        for (Contact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (contact.getFirstName().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Имя должно быть заполнено.");
            return contactValidation;
        }

        if (contact.getLastName().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Фамилия должно быть заполнено.");
            return contactValidation;
        }

        if (contact.getPhone().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Телефон должно быть заполнено.");
            return contactValidation;
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");
            return contactValidation;
        }
        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);
        if (contactValidation.isValid()) {
            contactDao.add(contact);
        }
        return contactValidation;
    }

    public void deleteContactById(int id) {
        contactDao.deleteById(id);
    }

    public void deleteContactsbyIds(int[] ids) {
        for (int id : ids) {
            deleteContactById(id);
        }
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public List<Contact> getFilteredContacts(String substring) {
        return getAllContacts().stream().filter(x ->
                (x.getFirstName().contains(substring) ||
                        x.getLastName().contains(substring) ||
                        x.getPhone().contains(substring))
        ).collect(Collectors.toList());
    }
}
