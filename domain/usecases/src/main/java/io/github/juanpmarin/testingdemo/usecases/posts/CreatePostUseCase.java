package io.github.juanpmarin.testingdemo.usecases.posts;

import io.github.juanpmarin.testingdemo.model.posts.CreatePostRequest;
import io.github.juanpmarin.testingdemo.model.posts.Post;
import io.github.juanpmarin.testingdemo.model.posts.PostsGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class CreatePostUseCase {

    private final PostsGateway postsGateway;


    public Mono<Post> create(CreatePostRequest request) {
        return validateRequest(request)
                .map(r -> new Post(null, r.getTitle(), r.getContent()))
                .flatMap(postsGateway::save);
    }

    private Mono<CreatePostRequest> validateRequest(CreatePostRequest request) {
        return Mono.just(request)
                .filter(r -> r.getContent() != null && r.getContent().length() < 60)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("content length must be less than 60")));
    }

}
