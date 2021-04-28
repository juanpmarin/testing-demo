package io.github.juanpmarin.testingdemo.usecases.posts;

import io.github.juanpmarin.testingdemo.model.posts.Comment;
import io.github.juanpmarin.testingdemo.model.posts.CommentsGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FindPostCommentsUseCaseTest {

    @Mock
    private CommentsGateway commentsGateway;

    @InjectMocks
    private FindPostCommentsUseCase findPostCommentsUseCase;


    @ParameterizedTest
    @ValueSource(strings = {"324", "1234", "5"})
    void shouldFindPostCommentsByPostIdSuccessfully(String postId) {
        var comments = Arrays.asList(
                new Comment(postId, "Jonathan", "Oelo"),
                new Comment(postId, "Ronal", "Saludos")
        );

        when(commentsGateway.findByPostId(postId))
            .thenReturn(Flux.fromIterable(comments));

        // Act
        StepVerifier.create(findPostCommentsUseCase.findByPostId(postId))
                .expectNextSequence(comments)
                .verifyComplete();

        // Assert
        verify(commentsGateway)
                .findByPostId(postId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"321543", "43214"})
    void shouldThrowExceptionIfPostIdLengthIsGreaterThan4(String postId) {
        // Act
        StepVerifier.create(findPostCommentsUseCase.findByPostId(postId))
                .expectError(IllegalArgumentException.class)
                .verify();

        // Assert
        verifyNoInteractions(commentsGateway);
    }
}