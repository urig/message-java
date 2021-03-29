package io.urig.messages;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessagesRepository {

    public List<Message> getMessages(String recipient) {
        return new ArrayList<Message>() {
            {
                add(new Message());
            }
        };
    }
}
