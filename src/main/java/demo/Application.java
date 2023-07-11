package demo;

import auto.AutoClient;
import demo.client.Newspapers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@Slf4j
@RegisterReflectionForBinding({Points.class, Points.Geometry.class, Points.PointsProperties.class, Forecast.class,
        Forecast.ForecastProperties.Period.class, Forecast.ForecastProperties.class})
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    Newspapers newspapers;

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

    @GetMapping
    String test() {
        return newspapers.newspapers().get("newspapers").subList(0, 5)
                .toString();
    }

}

@AutoClient
@Qualifier("webClient")
@HttpExchange("https://jsonplaceholder.typicode.com")
interface Todos {

    @GetExchange("/todos/{id}")
    Todo todoById(@PathVariable int id);

}


@Qualifier("webClient")
@AutoClient
@HttpExchange("https://api.weather.gov")
interface Weather {

    @GetExchange("/points/{latitude},{longitude}")
    Points points(@PathVariable double latitude, @PathVariable double longitude);

    @GetExchange("/gridpoints/{office}/{gridX},{gridY}/forecast")
    Forecast forecast(@PathVariable String office, @PathVariable int gridX, @PathVariable int gridY);

}

record Forecast(ForecastProperties properties) {
    record ForecastProperties(Period[] periods) {
        record Period(int number, String name, String startTime, String endTime, boolean isDaytime, float temperature,
                      String temperatureUnit) {
        }
    }
}

record Points(PointsProperties properties, Geometry geometry) {

    record PointsProperties(String gridId, int gridX, int gridY) {
    }

    record Geometry(String type, double[] coordinates) {
    }

}


record Todo(int userId, int id, String title, boolean completed) {
}
