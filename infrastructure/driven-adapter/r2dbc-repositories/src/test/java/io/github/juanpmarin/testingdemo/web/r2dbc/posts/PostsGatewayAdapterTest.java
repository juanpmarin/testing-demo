package io.github.juanpmarin.testingdemo.web.r2dbc.posts;

import io.github.juanpmarin.testingdemo.model.posts.Post;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;


@Testcontainers
@DataR2dbcTest
@AutoConfigurationPackage
@ContextConfiguration(classes = {PostsGatewayAdapter.class, PostsDataRepository.class, PostsConverterImpl.class})
class PostsGatewayAdapterTest {

    @Autowired
    private PostsGatewayAdapter postsGatewayAdapter;

    @Autowired
    private DatabaseClient databaseClient;

    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> postgreSQLContainer.getJdbcUrl().replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    void shouldInsertNewPost() {
        var post = new Post(null, "test", "hello");

        databaseClient.sql("CREATE TABLE IF NOT EXISTS posts\n" +
                "(\n" +
                "    id      serial PRIMARY KEY,\n" +
                "    title   varchar(64) UNIQUE NOT NULL,\n" +
                "    content text               NOT NULL\n" +
                ");\n").fetch().one().block();

        StepVerifier.create(postsGatewayAdapter.save(post))
                .expectNextCount(1)
                .verifyComplete();
    }
}