package sk.tuke.kpi.kp.labyrintmaze.service;

public class RatingException extends RuntimeException{
    public RatingException() {
    }

    public RatingException(String message) {
        super(message);
    }

    public RatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RatingException(Throwable cause) {
        super(cause);
    }
}
