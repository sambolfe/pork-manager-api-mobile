package br.csi.porkManagerApi.exceptions;

public class InvalidEnumException extends RuntimeException{
    public InvalidEnumException(String message) {
        super(message);
    }
}
