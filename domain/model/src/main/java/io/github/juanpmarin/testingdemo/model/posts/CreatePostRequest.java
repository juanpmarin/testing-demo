package io.github.juanpmarin.testingdemo.model.posts;

import lombok.Value;

@Value
public class CreatePostRequest {

    String title;
    String content;

}
