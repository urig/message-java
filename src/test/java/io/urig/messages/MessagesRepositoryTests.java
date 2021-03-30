package io.urig.messages;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MessagesRepositoryTests {

    @Test
    public void getMessages_newRecipient_emptyList() {
        var target = new MessagesRepository();
        var actual = target.getMessages("foo");
        assertThat(actual).isEmpty();

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    public void getMessages_badRecipient_throws(String recipient) {
        var target = new MessagesRepository();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    target.getMessages(recipient);
                });
    }

    @Test public void addMessage_newRecipient_messageIsAdded() {
        // Arrange
        var target = new MessagesRepository();
        var message = new Message() {{
            setRecipient("foo");
            setSender("bar");
            setContent("baz");
        }};
        // Act
        var actual = target.addMessage("buzz", message);
        // Assert
        assertThat(message.getRecipient()).isEqualTo("buzz");
        assertThat(message.getSender()).isEqualTo("bar");
        assertThat(message.getContent()).isEqualTo("baz");
    }

    @Test public void addMessage_existingRecipient_messageIsAdded() {
        // Arrange
        var target = new MessagesRepository();
        var message = new Message() {{
            setRecipient("foo");
            setSender("bar");
            setContent("baz");
        }};
        // Act
        target.addMessage("buzz", message);
        target.addMessage("buzz", message);
        // Assert
        var actual = target.getMessages("buzz");
        assertThat(actual).hasSize(2);
    }
}
