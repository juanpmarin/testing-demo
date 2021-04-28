package io.github.juanpmarin.testingdemo.model.posts;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostsGateway {

    Flux<Post> findAll();

    Mono<Post> save(Post post);

}
