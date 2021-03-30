package io.urig.messages;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessagesController {

    private final MessagesRepository repository;

    MessagesController(MessagesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/messages/{recipient}")
    List<Message> getMessages(@PathVariable String recipient) {
        return repository.getMessages(recipient);
    }

}
