package io.github.juanpmarin.testingdemo.usecases.posts;

import io.github.juanpmarin.testingdemo.model.posts.Comment;
import io.github.juanpmarin.testingdemo.model.posts.CommentsGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class FindPostCommentsUseCase {

    private final CommentsGateway commentsGateway;


    public Flux<Comment> findByPostId(String postId) {
        return Mono.just(postId)
                .doOnNext(this::verifyIdLength)
                .flatMapMany(commentsGateway::findByPostId);
    }

    private void verifyIdLength(String id) {
        if (id.length() > 4) {
            throw new IllegalArgumentException();
        }
    }

}
