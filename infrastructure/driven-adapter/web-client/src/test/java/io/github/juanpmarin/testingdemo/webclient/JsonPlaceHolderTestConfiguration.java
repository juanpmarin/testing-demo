package io.github.juanpmarin.testingdemo.webclient;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@TestConfiguration
public class JsonPlaceHolderTestConfiguration {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
