package demo.client;

import auto.AutoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.net.URL;
import java.util.List;
import java.util.Map;

@Qualifier("large")
@AutoClient
@HttpExchange("https://chroniclingamerica.loc.gov")
public
interface Newspapers {

    @GetExchange("/newspapers.json")
    Map<String, List<Newspaper>> newspapers();

}

record Newspaper(String lccn, String state, URL url, String title) {
}