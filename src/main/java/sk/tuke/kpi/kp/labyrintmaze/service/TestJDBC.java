package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;

import java.sql.DriverManager;
import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception{
        RatingService service=new RatingServiceJDBC();
        service.reset();
        service.setRating(new Rating("Matej","labyrint-maze",5,new Date()));
        Rating rating=new Rating("Kubo","labyrint-maze",1,new Date());
        service.setRating(rating);
        service.setRating(new Rating("Miro","labyrint-maze",2,new Date()));
        service.setRating(new Rating("Filip","labyrint-maze",4,new Date()));
        var scores=service.getRating("labyrint-maze","Kubo");
        System.out.println(scores);
        service.setRating(rating);
        scores=service.getAverageRating("labyrint-maze");
        System.out.println(scores);
    }
}