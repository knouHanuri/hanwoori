package knou.seoul.hanwoori.common.exception;

public class EncryptionException extends RuntimeException{
    public EncryptionException(String message) {
        super(message);
    }
    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

}
