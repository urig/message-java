package io.urig.messages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessagesController {

    private final MessagesRepository repository;

    MessagesController(MessagesRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = {"/messages/{recipient}", "/messages/"})
    ResponseEntity<List<Message>> getMessages(@PathVariable String recipient) {
        try {

            var messages = repository.getMessages(recipient);
            return ResponseEntity.ok(messages);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = {"/messages/{recipient}", "/messages/"})
    ResponseEntity<Message> addMessage(@PathVariable String recipient, @RequestBody Message message) {
        try {

            var result = repository.addMessage(recipient, message);
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
