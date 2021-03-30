package io.urig.messages;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagesRepository {

    private final ConcurrentHashMap<String, List<Message>> store;

    public MessagesRepository() {
        store = new ConcurrentHashMap<>();
    }

    public List<Message> getMessages(String recipient) {
        validateRecipient(recipient);
        return store.getOrDefault(recipient, new ArrayList<>());
    }

    public Message addMessage(String recipient, Message message) {
        validateRecipient(recipient);
        validateMessage(message);
        setMetadataOnMessage(message, recipient);
        var newList = new ArrayList<Message>() {
            {
                add(message);
            }
        };
        store.merge(recipient, newList, (v1, v2) ->
        {
            v1.add(message);
            return v1;
        });
        return message;
    }

    private void setMetadataOnMessage(Message message, String recipient) {
        message.setId(UUID.randomUUID());
        message.setRecipient(recipient);
    }

    private static void validateRecipient(String recipient) {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("recipient cannot be null or empty");
        }
    }

    private static void validateMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
    }
}
