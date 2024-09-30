package sk.tuke.kpi.kp.labyrintmaze;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.labyrintmaze.server.webservice.CommentServiceRest;
import sk.tuke.kpi.kp.labyrintmaze.service.*;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
//@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.kpi.kp.labyrintmaze.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        //SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner runner(){
        return s-> console().play();
    }
    @Bean
    public ConsoleUI console(){
        return new ConsoleUI();
    }
    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService CommentService(){
        return new CommentServiceRestClient();
    }
    @Bean
    public RatingService RatingService(){return new RatingServiceRestClient(); }
    @Bean
    public RestTemplate restTemplate() {return new RestTemplate();}


}
