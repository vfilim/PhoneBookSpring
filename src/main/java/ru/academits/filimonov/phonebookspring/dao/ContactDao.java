package ru.academits.filimonov.phonebookspring.dao;

import ru.academits.filimonov.phonebookspring.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Integer> {
    List<Contact> getAllContacts();

    void add(Contact contact);

    void deleteById(int id);

    List<Contact> findByPhone(String phone);
}
