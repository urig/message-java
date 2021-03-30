package io.urig.messages;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MessagesRepositoryTests {

    @Test
    public void getMessages_newRecipient_emptyList() {
        MessagesRepository target = new MessagesRepository();
        List<Message> actual = target.getMessages("foo");
        assertThat(actual).isEmpty();

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    public void getMessages_badRecipient_throws(String recipient) {
        MessagesRepository target = new MessagesRepository();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    target.getMessages(recipient);
                });
    }
}
