package io.urig.messages;

import jdk.jfr.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
		String url = "http://localhost:" + port + "/messages/foo";
		String actual = this.restTemplate
				.getForObject(url, String.class);
		assertThat(actual).isEqualTo("[]");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "  ", "\t", "\n"})
	public void GET_badRecipient_403(String recipient) {
		String url = "http://localhost:" + port + "/messages/"+recipient;
		ResponseEntity<Void> actual = this.restTemplate
				.exchange(url, HttpMethod.GET, null, Void.class, new Object());
				//.getForObject("http://localhost:" + port + "/messages/"+recipient, String.class);
		assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
