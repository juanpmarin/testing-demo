package io.github.juanpmarin.testingdemo.web.r2dbc.posts;

import io.github.juanpmarin.testingdemo.model.posts.Post;
import io.github.juanpmarin.testingdemo.model.posts.PostsGateway;
import io.github.juanpmarin.testingdemo.web.r2dbc.AdapterOperations;
import org.springframework.stereotype.Repository;


@Repository
public class PostsGatewayAdapter extends AdapterOperations<Post, PostData, Long, PostsDataRepository>
        implements PostsGateway {

    public PostsGatewayAdapter(PostsDataRepository repository, PostsConverter postsConverter) {
        super(repository, postsConverter::toData, postsConverter::toEntity);
    }

}
