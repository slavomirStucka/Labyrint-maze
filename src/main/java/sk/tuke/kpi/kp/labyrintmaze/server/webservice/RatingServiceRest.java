package sk.tuke.kpi.kp.labyrintmaze.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.service.CommentService;
import sk.tuke.kpi.kp.labyrintmaze.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player){
        return ratingService.getRating(game,player);
    }
    @GetMapping("/{game}/average")
    public int getAverageRating(@PathVariable String game){
        return ratingService.getAverageRating(game);
    }
    @PostMapping("/{game}")
    public void setRating(@RequestBody Rating rating){
         ratingService.setRating(rating);
    }
}
