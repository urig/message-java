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

    @Test
    public void addMessage_existingRecipient_messageIsAdded() {
        // Arrange
        var target = new MessagesRepository();
        var message1 = new Message() {{
            setRecipient("foo");
            setSender("bar");
            setContent("message 1");
        }};
        var message2 = new Message() {{
            setRecipient("foo");
            setSender("bar");
            setContent("message 2");
        }};
        // Act
        target.addMessage("buzz", message1);
        target.addMessage("buzz", message2);
        // Assert
        var actual = target.getMessages("buzz");
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getContent()).isEqualTo("message 1");
        assertThat(actual.get(1).getContent()).isEqualTo("message 2");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    public void addMessage_badRecipient_throws(String recipient) {
        var target = new MessagesRepository();
        var message = new Message();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    target.addMessage(recipient, message);
                });
    }

    @Test
    public void addMessage_nullMessage_throws() {
        var target = new MessagesRepository();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    target.addMessage("foo", null);
                });
    }

}
