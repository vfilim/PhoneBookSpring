package ru.academits.filimonov.phonebookspring;

import ru.academits.filimonov.phonebookspring.converter.ContactConverter;
import ru.academits.filimonov.phonebookspring.converter.ContactValidationConverter;
import ru.academits.filimonov.phonebookspring.dao.ContactDao;
import ru.academits.filimonov.phonebookspring.service.ContactService;

/**
 * Created by Anna on 14.06.2017.
 */
public class PhoneBook {
    public static ContactDao contactDao = new ContactDao();

    public static ContactService phoneBookService = new ContactService();

    public static ContactConverter contactConverter = new ContactConverter();

    public static ContactValidationConverter contactValidationConverter = new ContactValidationConverter();
}
