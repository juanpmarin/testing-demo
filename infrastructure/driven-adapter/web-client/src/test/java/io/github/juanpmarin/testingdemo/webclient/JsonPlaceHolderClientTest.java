package io.github.juanpmarin.testingdemo.webclient;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.juanpmarin.testingdemo.model.posts.Comment;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        classes = {
                JsonPlaceHolderTestConfiguration.class,
                JsonPlaceHolderPropertiesConfiguration.class,
                JsonPlaceHolderClient.class
        }
)
public class JsonPlaceHolderClientTest {

    private static MockWebServer mockWebServer;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private JsonPlaceHolderClient jsonPlaceHolderClient;


    @BeforeAll
    static void beforeClass() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void provideJsonPlaceHolderBaseUrl(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("json-placeholder.baseUrl",
                () -> String.format("http://localhost:%d/", mockWebServer.getPort()));
    }

    @Test
    void shouldFindCommentsByPostId() throws JsonProcessingException, InterruptedException {
        System.out.println("TEST");
        // Arrange
        var postId = "1";
        var response = Arrays.asList(
                new JsonPlaceHolderComment(1, 2, "Juanca", "juanca@bancolombia.com", "Contenido"),
                new JsonPlaceHolderComment(2, 3, "David", "david@bancolombia.com", "Comentario")
        );

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        var expectedResponse = Arrays.asList(
                new Comment("2", "Juanca", "Contenido"),
                new Comment("3", "David", "Comentario")
        );

        // Act
        StepVerifier.create(jsonPlaceHolderClient.findByPostId(postId))
                .expectNextSequence(expectedResponse)
                .verifyComplete();

        // Verify
        var request = mockWebServer.takeRequest();

        assertThat(request.getPath())
                .isEqualTo("/posts/1/comments");

        assertThat(request.getMethod())
                .isEqualTo("GET");

        assertThat(request.getHeader(HttpHeaders.ACCEPT))
                .isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    }

}
