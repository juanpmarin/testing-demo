package io.github.juanpmarin.testingdemo.model.posts;

import lombok.Value;


@Value
public class Post {

    Long id;
    String title;
    String content;

}
