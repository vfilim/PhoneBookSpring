package ru.academits.filimonov.phonebookspring.dao;

import org.springframework.stereotype.Repository;
import ru.academits.filimonov.phonebookspring.model.Contact;

import java.util.List;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {
    public ContactDaoImpl() {
        super(Contact.class);
//        Contact contact = new Contact();
//        contact.setFirstName("Иван");
//        contact.setLastName("Иванов");
//        contact.setPhone("9123456789");
//
//        create(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return findAll();
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        return null;
    }

    @Override
    public void add(Contact contact) {
        create(contact);
    }

    @Override
    public void deleteById(int id) {

    }
}
