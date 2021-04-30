package io.github.juanpmarin.testingdemo.webclient;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JsonPlaceHolderProperties.class)
public class JsonPlaceHolderPropertiesConfiguration {
}
