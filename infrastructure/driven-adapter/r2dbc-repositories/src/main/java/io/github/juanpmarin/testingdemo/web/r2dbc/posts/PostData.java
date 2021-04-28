package io.github.juanpmarin.testingdemo.web.r2dbc.posts;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Value
@Table("posts")
public class PostData {

    @Id
    Long id;
    String title;
    String content;

}
