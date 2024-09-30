package sk.tuke.kpi.kp.labyrintmaze.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.labyrintmaze.SpringClient;
import sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.labyrintmaze.service.*;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
@Configuration
@EntityScan(basePackages = "sk.tuke.kpi.kp.labyrintmaze.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);
    }
    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService CommentService(){
        return new CommentServiceJPA();
    }
    @Bean
    public RatingService RatingService(){return new RatingServiceJPA(); }

    @Bean
    public HracService HracService(){return new HracServiceJPA();}
}
