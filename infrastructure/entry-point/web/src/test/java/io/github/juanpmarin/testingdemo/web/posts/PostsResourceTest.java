package io.github.juanpmarin.testingdemo.web.posts;

import io.github.juanpmarin.testingdemo.model.posts.CreatePostRequest;
import io.github.juanpmarin.testingdemo.model.posts.Post;
import io.github.juanpmarin.testingdemo.usecases.posts.CreatePostUseCase;
import io.github.juanpmarin.testingdemo.usecases.posts.FindPostsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest
@AutoConfigurationPackage
@ContextConfiguration(classes = {PostsResource.class, PostsHandler.class})
class PostsResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FindPostsUseCase findPostsUseCase;

    @MockBean
    private CreatePostUseCase createPostUseCase;


    @Test
    void shouldFindAll() {
        var posts = List.of(
                new Post(1L, "hi!", "Hola"),
                new Post(2L, "Test", "hi")
        );

        when(findPostsUseCase.all())
                .thenReturn(Flux.fromIterable(posts));

        webTestClient.get()
                .uri("/posts")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Post.class)
                .isEqualTo(posts);
    }

    @Test
    void shouldCreatePost() {
        var request = new CreatePostRequest("test", "content");
        var post = new Post(1L, request.getTitle(), request.getContent());

        when(createPostUseCase.create(request))
                .thenReturn(Mono.just(post));

        webTestClient.post()
                .uri("/posts")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Post.class)
                .isEqualTo(post);
    }

}
