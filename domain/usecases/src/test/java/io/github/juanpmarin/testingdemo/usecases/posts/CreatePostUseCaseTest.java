package io.github.juanpmarin.testingdemo.usecases.posts;

import io.github.juanpmarin.testingdemo.model.posts.CreatePostRequest;
import io.github.juanpmarin.testingdemo.model.posts.Post;
import io.github.juanpmarin.testingdemo.model.posts.PostsGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private PostsGateway postsGateway;

    @InjectMocks
    private CreatePostUseCase createPostUseCase;


    @Test
    void shouldSavePostUsingRepository() {
        // Arrange
        String title = "my title";
        String content = "my content";

        var request = new CreatePostRequest(title, content);
        var newPost = new Post(null, title, content);
        var savedPost = new Post(2L, request.getTitle(), request.getContent());

        when(postsGateway.save(newPost))
                .thenReturn(Mono.just(savedPost));

        // Act
        StepVerifier.create(createPostUseCase.create(request))
                .expectNext(savedPost) // Assert
                .verifyComplete();

        // Assert
        verify(postsGateway).save(newPost);
    }

    @Test
    void shouldThrowExceptionIfContentLengthIsGreaterThan60() {
        // Arrange
        String title = "my title";
        String content = "a".repeat(61);

        var request = new CreatePostRequest(title, content);

        // Act
        StepVerifier.create(createPostUseCase.create(request))
                .expectErrorMessage("content length must be less than 60") // Assert
                .verify();
    }
}