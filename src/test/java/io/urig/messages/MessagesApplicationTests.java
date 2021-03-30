package io.urig.messages;

import jdk.jfr.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Category("Integration")
class MessagesApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void GET_newRecipient_emptyList() {
        var url = "http://localhost:" + port + "/messages/foo";
        var actual = this.restTemplate.getForObject(url, String.class);
        assertThat(actual).isEqualTo("[]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", "\n"})
    public void GET_badRecipient_403(String recipient) {
        var url = "http://localhost:" + port + "/messages/" + recipient;
        var actual = this.restTemplate
                .exchange(url, HttpMethod.GET, null, Void.class, new Object());
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
