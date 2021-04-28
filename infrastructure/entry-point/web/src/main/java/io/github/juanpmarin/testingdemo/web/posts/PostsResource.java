package io.github.juanpmarin.testingdemo.web.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@RequiredArgsConstructor
public class PostsResource {

    private final PostsHandler postsHandler;


    @Bean
    public RouterFunction<ServerResponse> postsRoutes() {
        return route()
                .path("/posts", builder -> builder
                        .GET(postsHandler::getAll)
                        .GET("/{id}", postsHandler::findById)
                        .POST(postsHandler::create)
                )
                .build();
    }

}
