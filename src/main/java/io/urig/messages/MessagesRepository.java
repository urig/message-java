package io.urig.messages;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagesRepository {

    private ConcurrentHashMap<String, List<Message>> store;

    public MessagesRepository() {
        store = new ConcurrentHashMap<>();
    }

    public List<Message> getMessages(String recipient) {
        validateRecipient(recipient);
        return store.getOrDefault(recipient, new ArrayList<Message>());
    }

    private static void validateRecipient(String recipient)
    {
        if (recipient == null || recipient.isBlank())
        {
            throw new IllegalArgumentException("recipient cannot be null or empty");
        }
    }
}
