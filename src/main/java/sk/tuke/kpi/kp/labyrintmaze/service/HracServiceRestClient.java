package sk.tuke.kpi.kp.labyrintmaze.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.labyrintmaze.entity.Hrac;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;

import java.util.Arrays;
import java.util.List;

public class HracServiceRestClient implements HracService{

    private String url = "http://localhost:8080/api/hrac";

    private RestTemplate restTemplate=new RestTemplate();
    @Override
    public String getHeslo(String name) throws RatingException {
        List<Hrac> hraci  = Arrays.asList(restTemplate.getForEntity(url , Hrac[].class).getBody());
        for (Hrac hrac : hraci) {
            if (hrac.getName().equals(name)) {
                return hrac.getHeslo();
            }
        }
        return null;
    }

    @Override
    public void addHrac(Hrac hrac) {
        restTemplate.postForEntity(url ,hrac, Hrac.class);
    }

    @Override
    public Hrac getHrac(String name) {
        List<Hrac> hraci  = Arrays.asList(restTemplate.getForEntity(url , Hrac[].class).getBody());
        for (Hrac hrac : hraci) {
            if (hrac.getName().equals(name)) {
                return hrac;
            }
        }
        return null;
    }
}
