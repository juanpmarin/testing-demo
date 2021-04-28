package io.github.juanpmarin.testingdemo.model.posts;

import reactor.core.publisher.Flux;

public interface CommentsGateway {
    Flux<Comment> findByPostId(String postId);
}
