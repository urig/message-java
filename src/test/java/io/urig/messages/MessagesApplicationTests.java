package io.urig.messages;

import jdk.jfr.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

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
		String actual = this.restTemplate
				.getForObject("http://localhost:" + port + "/messages/foo", String.class);
		assertThat(actual).isEqualTo("[]");
	}
}
