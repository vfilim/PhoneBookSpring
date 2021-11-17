package ru.academits.filimonov.phonebookspring.dao;

import org.springframework.stereotype.Repository;
import ru.academits.filimonov.phonebookspring.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Integer> implements ContactDao {
    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getAllContacts() {
        return findAll();
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        return getAllContacts()
                .stream()
                .filter(x -> x.getPhone().contains(phone))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Contact contact) {
        create(contact);
    }

    @Override
    public void deleteById(int id) {
        remove(getById(id));
    }
}
