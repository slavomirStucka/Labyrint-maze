package sk.tuke.kpi.kp.labyrintmaze.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;

import java.util.Arrays;
import java.util.List;

public class RatingServiceRestClient implements RatingService{
    private String url = "http://localhost:8080/api/rating";

    private RestTemplate restTemplate=new RestTemplate();
    @Override
    public void setRating(Rating rating) throws RatingException{
        try {
            restTemplate.postForObject(url+"/"+rating.getGame(), rating, Void.class);
        }catch(Exception e){
            throw new RatingException("SetRating problem",e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try {
            return restTemplate.getForObject(url + "/" + game + "/average" , Integer.class);
        }catch(Exception e){
            throw new RatingException("getAverageRating problem",e);
        }
        /*List<Rating> ratings  = Arrays.asList(restTemplate.getForEntity(url + "/" + game, Rating[].class).getBody());
        int sum=0;
        for (Rating rating : ratings) {
            sum+=rating.getRating();
        }
        int averageRating = sum / ratings.size();
        return averageRating;*/
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        List<Rating> ratings  = Arrays.asList(restTemplate.getForEntity(url + "/" + game, Rating[].class).getBody());
        for (Rating rating : ratings) {
            if (rating.getPlayer().equals(player)) {
                return rating.getRating();
            }
        }
        return 0;
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Reset is not supported on web interface.");
    }
}
