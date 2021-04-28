package io.github.juanpmarin.testingdemo.web.posts;

import io.github.juanpmarin.testingdemo.model.posts.CreatePostRequest;
import io.github.juanpmarin.testingdemo.model.posts.Post;
import io.github.juanpmarin.testingdemo.usecases.posts.CreatePostUseCase;
import io.github.juanpmarin.testingdemo.usecases.posts.FindPostsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PostsHandler {

    private final FindPostsUseCase findPostsUseCase;
    private final CreatePostUseCase createPostUseCase;


    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(findPostsUseCase.all(), Post.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return Mono.empty();
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreatePostRequest.class)
                .flatMap(createPostUseCase::create)
                .flatMap(createdPost -> ServerResponse.created(URI.create("posts")
                        .resolve(createdPost.getId().toString()))
                        .bodyValue(createdPost));
    }

}
