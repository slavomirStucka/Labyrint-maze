package sk.tuke.kpi.kp.labyrintmaze.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.service.RatingService;
import sk.tuke.kpi.kp.labyrintmaze.service.RatingServiceJDBC;

import java.sql.Timestamp;
import java.util.List;

public class RatingServiceTest {
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        ratingService = new RatingServiceJDBC();
        ratingService.reset();
    }

    @Test
    public void testSetRating() {
        Rating rating = new Rating("player1", "game1", 5, new Timestamp(System.currentTimeMillis()));
        ratingService.setRating(rating);
        int value = ratingService.getRating("game1","player1");
        Assertions.assertEquals(5, value);
    }

    @Test
    public void testAverageRating() {
        Rating rating1 = new Rating("player1", "game1", 5, new Timestamp(System.currentTimeMillis()));
        Rating rating2 = new Rating("player2", "game1", 4, new Timestamp(System.currentTimeMillis()));
        Rating rating3 = new Rating("player3", "game1", 3, new Timestamp(System.currentTimeMillis()));

        ratingService.setRating(rating1);
        ratingService.setRating(rating2);
        ratingService.setRating(rating3);

        int averageRating= ratingService.getAverageRating("game1");
        Assertions.assertEquals(4, averageRating);
    }
    @Test
    public void testRewriteRating() {
        Rating rating1 = new Rating("player1", "game1", 5, new Timestamp(System.currentTimeMillis()));
        ratingService.setRating(rating1);
        int rating= ratingService.getRating("game1","player1");
        Assertions.assertEquals(5, rating);

        Rating rating2 = new Rating("player1", "game1", 4, new Timestamp(System.currentTimeMillis()));
        ratingService.setRating(rating2);
        rating= ratingService.getRating("game1","player1");
        Assertions.assertEquals(4, rating);

        Rating rating3 = new Rating("player1", "game1", 3, new Timestamp(System.currentTimeMillis()));
        ratingService.setRating(rating3);
        rating= ratingService.getRating("game1","player1");
        Assertions.assertEquals(3, rating);
    }

    @Test
    public void testReset() {
        Rating rating = new Rating("player1", "game1", 5, new Timestamp(System.currentTimeMillis()));
        ratingService.setRating(rating);

        ratingService.reset();
        var rate = ratingService.getRating("game1","player1");
        Assertions.assertEquals(0, rate);
    }
}

