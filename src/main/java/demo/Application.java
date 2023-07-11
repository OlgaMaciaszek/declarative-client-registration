package demo;

import demo.client.Newspapers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    Newspapers newspapers;


    @GetMapping
    String test() {
        newspapers.newspapers()
                .map(news -> news.get("newspapers"))
                .subscribe(newspapers1 -> System.out.println("Title: " + newspapers1.get(0).title()));
        return "test";
    }

}

