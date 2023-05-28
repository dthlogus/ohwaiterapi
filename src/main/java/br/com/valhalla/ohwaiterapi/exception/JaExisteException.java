package br.com.valhalla.ohwaiterapi.exception;

public class JaExisteException extends RuntimeException{

    public JaExisteException(String message) {
        super(message);
    }
}
