package io.github.juanpmarin.testingdemo.webclient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "json-placeholder")
public class JsonPlaceHolderProperties {

    String baseUrl;

}
