package sk.tuke.kpi.kp.labyrintmaze.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;
import sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core.*;
import sk.tuke.kpi.kp.labyrintmaze.service.CommentService;
import sk.tuke.kpi.kp.labyrintmaze.service.RatingService;
import sk.tuke.kpi.kp.labyrintmaze.service.ScoreService;

import java.util.*;

@Controller
@RequestMapping("/labyrint-maze")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LabyrintMazeController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserController userController;
    Field field ;
    Direction direction=null;

    boolean setDifficulty=false;
    Date starthry;
    Date koniechry;
    private boolean krokSpat=false;
    private Stack<Direction> pohyby = new Stack<>();
    private int level;


    public boolean getSetDifficulty(){
        return setDifficulty;
    }
    @RequestMapping("/hard")
    public String hard(){
        field=new Field(31,42,'H');
        level=field.getLevel();
        setDifficulty=true;
        starthry=new Date();
        return "redirect:/labyrint-maze";
    }
    @RequestMapping("/easy")
    public String easy(){
        field=new Field(15,15,'E');
        level=field.getLevel();
        setDifficulty=true;
        starthry=new Date();
        return "redirect:/labyrint-maze";
    }
    @RequestMapping("/medium")
    public String medium(){
        field=new Field(19,36,'M');
        level=field.getLevel();
        setDifficulty=true;
        starthry=new Date();
        return "redirect:/labyrint-maze";
    }
    @RequestMapping("/addComment")
    public String addComment(String comment,Model model){
        if(userController.getLoggedUser()==null){
            commentService.addComment(new Comment("Anonymous", "labyrinth-maze", comment, new Date()));
        }else {
            commentService.addComment(new Comment(userController.getLoggedUser().getName(), "labyrinth-maze", comment, new Date()));
        }
        model.addAttribute("comments",commentService.getComments("labyrinth-maze"));
        model.addAttribute("score",commentService.getComments("labyrinth-maze"));
        return "redirect:/labyrint-maze";
    }

    @RequestMapping("/teleport")
    public String teleport(){
        int posY= field.getPosXofFinish();
        int posX= field.getPosYofFinish();
        if(field.getTile(posX-1,posY) instanceof Path){
            field.player.teleportation(posX-1,posY);
        }
        else if(field.getTile(posX,posY-1) instanceof Path){
            field.player.teleportation(posX,posY-1);
        }
        else if(field.getTile(posX+1,posY) instanceof Path){
            field.player.teleportation(posX+1,posY);
        }
        else if(field.getTile(posX,posY+1) instanceof Path){
            field.player.teleportation(posX,posY+1);
        }

        return "labyrint";
    }

    @RequestMapping()
    public String labyrint(@RequestParam(required = false) Direction direction,Model model){
        if(direction!=null){
            this.direction=direction;
            field.player.move(direction);
            if(!krokSpat){
                pohyby.push(direction);
            }
            krokSpat=false;
        }
        model.addAttribute("scores",scoreService.getTopScores("labyrinth-maze"));
        model.addAttribute("comments",commentService.getComments("labyrinth-maze"));
        return "labyrint";
    }

    public String getAverageRating(){
        int average = ratingService.getAverageRating("labyrinth-maze");
        String stars = String.join("", Collections.nCopies(average, "⭐"));
        return stars;
    }
    @RequestMapping("/new")
    public String newGame(@RequestParam(required = false) Direction direction, Model model) {
        setDifficulty=false;
        return labyrint( direction, model);
    }

    public String getMoves() {
        int moves = field.player.getMoves();
        return String.valueOf(moves);
    }
    public int getIntMoves(){
        return field.player.getMoves();
    }
    @RequestMapping("/addScore")
    public String addScore(){
       scoreService.addScore(new Score(userController.getLoggedUser().getName(), "labyrinth-maze",getIntMoves(), new Date()));
        setDifficulty=false;
        return "redirect:/labyrint-maze";
    }
    @RequestMapping("/addRating")
    public String addRating(int rating){
        ratingService.setRating(new Rating(userController.getLoggedUser().getName(), "labyrinth-maze",rating, new Date()));
        return "redirect:/labyrint-maze";
    }


    public boolean isSolved() {
        if (Objects.equals(getState(), GameState.SOLVED.toString())) {
            return true;
        }
        return false;
    }

    public String getState() {
        return field.getState().toString();
    }

    public String getHtmlField(){
        StringBuilder sb = new StringBuilder();
        sb.append("<div style='color: white;'>Your score: "+getMoves().toString()+"</div>\n");
        sb.append("<div style='display: flex; flex-direction: column;'>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<div style='display: flex;'>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var tile = field.getTile(row, column);
                sb.append("<img src='/images/labyrint-maze/"+ getImageName(tile) +".png'>\n");
            }
            sb.append("</div>\n");
        }
        sb.append("</div>\n");

        return sb.toString();
    }

    private String getImageName(Tile tile) {
            if (tile instanceof Path) {
                if (((Path) tile).isHasPlayer()) {
                    return "player";
                } else {
                    return "path";
                }
            } else if (tile instanceof Finish) {
                if(field.getState()== GameState.PLAYING) {
                    return "finish";
                }else{
                    return "finished";

                }
            } else {//if (tile instanceof Wall) {
                return "wall";
            }
        }

}
