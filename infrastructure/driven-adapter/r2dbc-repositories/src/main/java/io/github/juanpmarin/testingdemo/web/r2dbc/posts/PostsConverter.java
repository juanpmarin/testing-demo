package io.github.juanpmarin.testingdemo.web.r2dbc.posts;


import io.github.juanpmarin.testingdemo.model.posts.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostsConverter {

    Post toEntity(PostData data);

    PostData toData(Post entity);

}
