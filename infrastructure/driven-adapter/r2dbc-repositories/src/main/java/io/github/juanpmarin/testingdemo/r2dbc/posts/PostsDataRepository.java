package io.github.juanpmarin.testingdemo.r2dbc.posts;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostsDataRepository extends ReactiveCrudRepository<PostData, Long> {
}
