package io.github.juanpmarin.testingdemo.usecases.posts;

import io.github.juanpmarin.testingdemo.model.posts.Post;
import io.github.juanpmarin.testingdemo.model.posts.PostsGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;


@RequiredArgsConstructor
public class FindPostsUseCase {

    private final PostsGateway postsGateway;


    public Flux<Post> all() {
        return postsGateway.findAll();
    }

}
