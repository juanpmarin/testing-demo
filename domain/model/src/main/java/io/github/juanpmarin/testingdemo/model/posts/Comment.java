package io.github.juanpmarin.testingdemo.model.posts;

import lombok.Value;


@Value
public class Comment {

    String postId;
    String userId;
    String content;

}
