package ru.academits.filimonov.phonebookspring.servlet;

import ru.academits.filimonov.phonebookspring.PhoneBook;
import ru.academits.filimonov.phonebookspring.converter.ContactConverter;
import ru.academits.filimonov.phonebookspring.model.Contact;
import ru.academits.filimonov.phonebookspring.service.ContactService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.List;

public class GetAllContactsServlet extends HttpServlet {
    private ContactService phoneBookService = PhoneBook.phoneBookService;
    private ContactConverter contactConverter = PhoneBook.contactConverter;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Contact> contactList = phoneBookService.getAllContacts();
            String contactListJson = contactConverter.convertToJson(contactList);
            resp.getOutputStream().write(contactListJson.getBytes(Charset.forName("UTF-8")));
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (Exception e) {
            System.out.println("error in GetAllContactsServlet GET: ");
            e.printStackTrace();
        }
    }
}
