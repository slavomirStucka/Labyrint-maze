package sk.tuke.kpi.kp.labyrintmaze.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;
import sk.tuke.kpi.kp.labyrintmaze.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {
    @Autowired
    private ScoreService scoreService;
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game){
        return scoreService.getTopScores(game);
    }
    @PostMapping("/{game}")
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }
}
