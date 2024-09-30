package sk.tuke.kpi.kp.labyrintmaze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService{
    private String url = "http://localhost:8080/api/score";

    //@Autowired
    private RestTemplate restTemplate=new RestTemplate();

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url + "/" + score.getGame(),score,Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported on web interface.");
    }
}
