
public class NumberException extends RuntimeException {
    
    public NumberException(String message) {
        super(message);
    }
    
    public NumberException(Throwable cause) {
        super(cause);
    }
    
    public NumberException(String message, Throwable cause) {
        super(message, cause);
    }
}