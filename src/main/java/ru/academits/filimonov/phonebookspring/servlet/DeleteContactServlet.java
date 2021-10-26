package ru.academits.filimonov.phonebookspring.servlet;

import ru.academits.filimonov.phonebookspring.PhoneBook;
import ru.academits.filimonov.phonebookspring.service.ContactService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteContactServlet extends HttpServlet {
    private ContactService phoneBookService = PhoneBook.phoneBookService;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            phoneBookService.deleteContactById(Integer.parseInt(req.getParameter("id")));
    }
}
