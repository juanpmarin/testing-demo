package io.github.juanpmarin.testingdemo.webclient;

import lombok.Value;


@Value
public class JsonPlaceHolderComment {

    Integer id;
    Integer postId;
    String name;
    String email;
    String body;

}
