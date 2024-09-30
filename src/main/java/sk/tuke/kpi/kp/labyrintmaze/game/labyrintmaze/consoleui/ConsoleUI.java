package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;
import sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core.*;
import sk.tuke.kpi.kp.labyrintmaze.service.*;

import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private Scanner scanner=new Scanner(System.in);
    @Autowired
    private ScoreService scoreService;//=new ScoreServiceJDBC();
    @Autowired
    private CommentService commentService;//=new CommentServiceJDBC();
    @Autowired
    private RatingService ratingService;//=new RatingServiceJDBC();
    private int moves=0;
    private int scoreKoeficient;

    public ConsoleUI(Field field){
        this.field=field;
    }
    public ConsoleUI(){

    }
    public void play(){
        //ratingService.setRating(new Rating("barofrajeris","labyrinth-maze",1,new Date()));
        printingMenu();
        menu();
        // field.load();
        while (field.getState()== GameState.PLAYING) {
            printing(field);
            handleInput();
            if(field.getState()==GameState.SOLVED){
                celebration();
                saveScoreCommentRating(moves);
                endOrContinue();
            } else if (field.getState()==GameState.FAILED) {
                System.err.println("\u001B[31mYou lost!\u001B[0m");
                endOrContinue();
            }
        }
    }


    private void printing(Field field){
        for (int i = 0; i < field.getRowCount(); i++) {
            System.out.print("|");
                for (int j = 0; j < field.getColumnCount(); j++) {
                    Tile aktualField = field.getTile(i, j);
                    if (aktualField instanceof Path) {
                        if (((Path) aktualField).isHasPlayer()) {
                            System.out.print("\u001B[31m\u001B[40m\uD83D\uDD25\033[0m");
                        } else {
                            System.out.print("\u001B[40m  \u001B[0m");
                        }
                    } else if (aktualField instanceof Finish) {
                        if(field.getState()==GameState.PLAYING) {
                            System.out.print("\033[40;1m##\033[0m");
                        }else{
                            System.out.print("\033[32m\033[40m##\033[0m");

                        }
                    } else if (aktualField instanceof Wall) {
                        System.out.print("\u001B[46m  \u001B[0m");
                    }
                }
                System.out.print("|");
                System.out.println();
        }
    }
    private void handleInput() {
        System.out.println("\u001B[32mEnter command (W-up,A-left,S-down,D-right,R- Back to menu,Q- quit game): \u001B[0m");
        var line=scanner.nextLine();
        if (moves>300){
            field.setState(GameState.FAILED);
        }
        switch (line) {
            case "Q":
            case "q":
                System.out.println("\u001B[31mEnding Game.\u001B[0m");
                //field.save();
                System.exit(0);
            case "R":
            case "r":
                play();
                break;
            case "W":
            case "w":
                field.player.move(Direction.NORTH);
                moves=moves+scoreKoeficient;
                break;
            case "S":
            case "s":
                field.player.move(Direction.SOUTH);
                moves=moves+scoreKoeficient;
                break;
            case "A":
            case "a":
                field.player.move(Direction.WEST);
                moves=moves+scoreKoeficient;
                break;
            case "D":
            case "d":
                field.player.move(Direction.EAST);
                moves=moves+scoreKoeficient;
                break;
            default:
                System.err.println("\u001B[31mBad input!\u001B[0m");
                handleInput();
                break;
        }
    }


    private void menu(){
        System.out.println("\u001B[32mPlease choose your difficulty. H- HARD, M-MEDIUM, E- EASY,C-Continue previous game, Q- quit game\u001B[0m");
        var line=scanner.nextLine();
        System.out.println("\u001B[36m════════════════════════════════════════════════════════════════════════════════\u001B[0m");
        switch (line) {
            case "H":
            case "h":
                System.out.println("\u001B[32mYou choose HARD. Good luck!\u001B[0m");
                field = new Field(31, 42, 'H');
                scoreKoeficient=1;
                break;
            case "M":
            case "m":
                System.out.println("\u001B[32mYou choose MEDIUM. Good luck!\u001B[0m");
                field = new Field(19, 36, 'M');
                scoreKoeficient=2;
                break;
            case "E":
            case "e":
                System.out.println("\u001B[32mYou choose Easy. Good luck!\u001B[0m");
                field = new Field(15, 15, 'E');
                scoreKoeficient=3;
                break;
            case "Q":
            case "q":
                System.out.println("\u001B[31mEnding game!\u001B[0m");
                System.exit(0);
            /*case "C":
            case "c":
                System.out.println("\u001B[31mContinue previous game!\u001B[0m");
                field.load();
                break;*/
            default:
                System.err.println("\u001B[31mBad input!\u001B[0m");
                menu();
                break;
        }
    }
    private void saveScoreCommentRating(int moves){
        System.out.println("\u001B[36m════════════════════════════════════════════════════════════════════════════════\u001B[0m");
        System.out.print("\u001B[32mENTER YOUR NAME: \u001B[0m");
        String name = scanner.nextLine();
        if(name.length() > 32){
            name = name.substring(0, 32);
        }
        scoreService.addScore(new Score(name,"labyrinth-maze",moves,new Date()));
        saveComment(name);
        rate(name);
    }
    private void saveComment(String name){
        System.out.println("\u001B[36m════════════════════════════════════════════════════════════════════════════════\u001B[0m");
        System.out.println("\u001B[32mWant to add comment? (Y-Yes, N-No) \u001B[0m");
        var line=scanner.nextLine();
        switch (line) {
            case "Y":
            case "y":
                System.out.println("\u001B[36m════════════════════════════════════════════════════════════════════════════════\u001B[0m");
                System.out.print("\u001B[32mENTER COMMENT: \u001B[0m");
                String comment = scanner.nextLine();
                if(comment.length() > 32){
                    comment = comment.substring(0, 32);
                }
                commentService.addComment(new Comment(name,"labyrinth-maze",comment,new Date()));
                break;
            case "N":
            case "n":
                break;
            default:
                System.err.println("\u001B[31mBad input!\u001B[0m");
                saveComment(name);
                break;
        }

    }
    private void rate(String name){
        System.out.println("\u001B[36m════════════════════════════════════════════════════════════════════════════════\u001B[0m");
        System.out.println("\u001B[32mRate this game(0-5): \u001B[0m");
        int line = scanner.nextInt();
        scanner.nextLine();
        if(line>5||line<0){
            System.err.println("\u001B[31mBad input!\u001B[0m");
            rate(name);
        }else {
            ratingService.setRating(new Rating(name, "labyrinth-maze", line, new Date()));
            System.out.println("\u001B[32m╔════════════════════\u001B[0m");
            System.out.print("\u001B[32m║\u001B[36mActual rating:  \u001B[0m");
            int rating= ratingService.getAverageRating("labyrinth-maze");
            for(int i=0;i<rating;i++){
                System.out.print("⭐");
            }
            System.out.println();
            System.out.println("\u001B[32m╚════════════════════\u001B[0m");
        }
    }
    private void  endOrContinue(){
        System.out.println("\u001B[36m════════════════════════════════════════════════════════════════════════════════\u001B[0m");
        System.out.println("\u001B[32mWant to continue? (C-continue, S-show TOPscores, K-show comments , Q- quit)\u001B[0m");
        var line=scanner.nextLine();
        switch (line) {
            case "c":
            case "C":
                moves = 0;
                play();
                break;
            case "Q":
            case "q":
                System.out.println("\u001B[31mEnding game!\u001B[0m");
                System.exit(0);
            case "S":
            case "s":
                printScores();
                endOrContinue();
                break;
            case "K":
            case "k":
                printComents();
                endOrContinue();
                break;
            default:
                System.err.println("\u001B[31mBad input!\u001B[0m");
                endOrContinue();
                break;
        }
    }
    private static String centerText(String text) {
        int width = 80;
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        for (int i = padding + text.length(); i < width; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
    private void printScores() {
        var scores = scoreService.getTopScores("labyrinth-maze");
        System.out.println("\u001B[36m╔════════════════════════════════════════\u001B[0m");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("\u001B[36m║%d. %s %d\n\u001B[0m", i + 1, score.getPlayer(), score.getPoints());
        }
        System.out.println("\u001B[36m╚════════════════════════════════════════\u001B[0m");
    }
    private void printComents(){
        var coments = commentService.getComments("labyrinth-maze");
        System.out.println("\u001B[32m╔════════════════════════════════════════\u001B[0m");
        for (int i = 0; i < coments.size(); i++) {
            var comment = coments.get(i);
            System.out.printf("\u001B[32m║%d. %s \"%s\"\n\u001B[0m", i + 1, comment.getPlayer(), comment.getComment());
        }
        System.out.println("\u001B[32m╚════════════════════════════════════════\u001B[0m");
    }


    private void printingMenu(){
        System.out.print("\033[44;1m");
        System.out.print(centerText("\033[30mWelcome to"));
        System.out.print("\033[0m\n");
        System.out.print("\033[40m");
        System.out.print(centerText("\033[32m ___      ___     ____     ________   _______ "));
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print(centerText("\033[32m|    \\   /   |   |    |   |____    | |  _____|"));
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print(centerText("\033[32m|     \\_/    |  |  /\\  |       /__/  |  |____ "));
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print(centerText("\033[32m|  | \\ _ /|  | |  |__|  |  __/  /__  |  |____ "));
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print(centerText("\033[32m|__|      |__| |__|  |__| |________| |_______|"));
        System.out.print("\033[0m\n");

    }

    private void celebration(){
        printing(field);
        System.out.println();
        System.out.print("\033[40;1m");
        System.out.print("\033[32m __     __   ____    _    _      __          __   ____    _   _    _ ");
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print("\033[32m \\ \\   / /  / __ \\  | |  | |     \\ \\        / /  / __ \\  | \\ | |  | |");
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print("\033[32m  \\ \\_/ /  | |  | | | |  | |      \\ \\  /\\  / /  | |  | | |  \\| |  | |");
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print("\033[32m   \\   /   | |  | | | |  | |       \\ \\/  \\/ /   | |  | | | . ` |  | |");
        System.out.print("\033[0m\n");
        System.out.print("\033[40;1m");
        System.out.print("\033[32m    |_|     \\____/   \\____/          \\/  \\/      \\____/  |_| \\_|  (_)");
        System.out.print("\033[0m\n");
    }
}
