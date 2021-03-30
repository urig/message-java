package io.urig.messages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.ok(
                    repository.getMessages(recipient));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
