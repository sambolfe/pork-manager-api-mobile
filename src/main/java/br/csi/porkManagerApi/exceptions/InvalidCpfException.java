package br.csi.porkManagerApi.exceptions;

public class InvalidCpfException extends RuntimeException{
    public InvalidCpfException(String message) {
        super(message);
    }
}
