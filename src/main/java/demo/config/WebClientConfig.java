package demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
public class WebClientConfig {

    @Bean
        // this has no explicit qualifier but we use a qualifier on interface and use
        // this beanName (fragile!)
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @Qualifier("large")
    WebClient largeMemoryWebClient(WebClient.Builder builder) {
        var size = 262144 * 10;
        var strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return builder.exchangeStrategies(strategies).build();
    }
}
