package io.urig.messages;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessagesController {

    private final MessagesRepository repository;
    private final Logger logger;

    MessagesController(MessagesRepository repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    @GetMapping(value = {"/messages/{recipient}", "/messages/"})
    ResponseEntity<List<Message>> getMessages(@PathVariable String recipient) {
        try {

            var messages = repository.getMessages(recipient);
            return ResponseEntity.ok(messages);

        } catch (IllegalArgumentException ex) {
            var error = String.format("Illegal argument in GET. recipient=[%s]", recipient);
            logger.error(error, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = {"/messages/{recipient}", "/messages/"})
    ResponseEntity<Message> addMessage(@PathVariable String recipient, @RequestBody Message message) {
        try {

            var result = repository.addMessage(recipient, message);
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException ex) {
            var error = String.format("Illegal argument in POST. recipient=[%s], message=[%s]", recipient, message);
            logger.error(error, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
