package ru.academits.filimonov.phonebookspring.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.academits.filimonov.phonebookspring.service.ContactService;

@EnableAutoConfiguration
@EnableScheduling
@Service
public class Scheduler {
    public static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    ContactService contactService;

    @Autowired
    public Scheduler(ContactService contactService) {
        this.contactService = contactService;
    }

    @Scheduled(fixedRate = 10000)
    public void deleteRandomContactScheduled() {
        logger.info("Scheduled!");

        contactService.deleteRandomContact();
    }
}
