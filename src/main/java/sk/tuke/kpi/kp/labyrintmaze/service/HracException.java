package sk.tuke.kpi.kp.labyrintmaze.service;

public class HracException extends RuntimeException {
    public HracException() {
        super();
    }

    public HracException(String message) {
        super(message);
    }

    public HracException(String message, Throwable cause) {
        super(message, cause);
    }

    public HracException(Throwable cause) {
        super(cause);
    }
}
