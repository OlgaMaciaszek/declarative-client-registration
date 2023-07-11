package demo.client;

import auto.AutoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Qualifier("large")
@AutoClient
@HttpExchange("https://chroniclingamerica.loc.gov")
public interface Newspapers {

    @GetExchange("/newspapers.json")
    Mono<Map<String, List<Newspaper>>> newspapers();


}

