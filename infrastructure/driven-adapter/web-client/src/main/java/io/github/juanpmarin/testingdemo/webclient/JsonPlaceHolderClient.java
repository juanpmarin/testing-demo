package io.github.juanpmarin.testingdemo.webclient;


import io.github.juanpmarin.testingdemo.model.posts.Comment;
import io.github.juanpmarin.testingdemo.model.posts.CommentsGateway;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class JsonPlaceHolderClient implements CommentsGateway {

    private final WebClient webClient;


    public JsonPlaceHolderClient(WebClient.Builder webClientBuilder,
                                 JsonPlaceHolderProperties jsonPlaceHolderProperties) {
        webClient = webClientBuilder.baseUrl(jsonPlaceHolderProperties.getBaseUrl()).build();
    }

    @Override
    public Flux<Comment> findByPostId(String postId) {
        return webClient.get()
                .uri("/posts/{id}/comments", postId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(JsonPlaceHolderComment.class)
                .map(this::mapToComment);
    }

    private Comment mapToComment(JsonPlaceHolderComment jsonPlaceHolderComment) {
        return new Comment(jsonPlaceHolderComment.getPostId().toString(),
                jsonPlaceHolderComment.getName(),
                jsonPlaceHolderComment.getBody());
    }

}
