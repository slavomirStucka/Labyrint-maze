package sk.tuke.kpi.kp.labyrintmaze.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Hrac;
import sk.tuke.kpi.kp.labyrintmaze.service.CommentService;
import sk.tuke.kpi.kp.labyrintmaze.service.HracService;
import sk.tuke.kpi.kp.labyrintmaze.service.RatingException;

@RestController
@RequestMapping("/api/hrac")
public class HracServiceRest {
    @Autowired
    private HracService hracService;
    @PostMapping
    public void addHrac(@RequestBody Hrac hrac) {
        hracService.addHrac(hrac);
    }

    public String getHeslo(@PathVariable String name){
        return hracService.getHeslo(name);
    }

    public Hrac getHrac(@PathVariable String name) {
        return hracService.getHrac(name);
    }
}
