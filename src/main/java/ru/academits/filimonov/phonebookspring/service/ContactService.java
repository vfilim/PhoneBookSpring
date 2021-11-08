package ru.academits.filimonov.phonebookspring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.academits.filimonov.phonebookspring.dao.ContactDao;
import ru.academits.filimonov.phonebookspring.model.Contact;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@EnableAutoConfiguration
@EnableScheduling
@Service
public class ContactService {
    static Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactDao contactDao;

    private static final Random randomizer = new Random();

    @Autowired
    public ContactService(ContactDao contactDao){
        this.contactDao = contactDao;
    }

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

    @Scheduled(fixedRate = 10000)
    public void deleteRandomContact() {
        List<Contact> contactList = getAllContacts();

        if (contactList.size() != 0) {
            Contact randomContact = contactList.get(randomizer.nextInt(contactList.size()));

            deleteContactById(randomContact.getId());

            log.info("Contact " + randomContact + " is randomly deleted");
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
