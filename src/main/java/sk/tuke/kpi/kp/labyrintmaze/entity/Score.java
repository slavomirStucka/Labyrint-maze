package sk.tuke.kpi.kp.labyrintmaze.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private String player;

    private String game;

    private int points;

    private Date playedon;


    public Score(){

    }
    public Score(String player, String game, int points, Date playedon) {
        this.player = player;
        this.game = game;
        this.points = points;
        this.playedon = playedon;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedon() {
        return playedon;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedon = playedon;
    }

    @Override
    public String toString() {
        return "Score{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", points=" + points +
                ", playedAt=" + playedon +
                '}';
    }
}

