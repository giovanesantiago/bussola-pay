package br.com.bussolapay.config.exceptions;

public class ClienteNotFoundException extends NotFoundException {

    public ClienteNotFoundException() {
    }

    public ClienteNotFoundException(String message) {
        super(message);
    }

    // TODO: handler para deslogar caso dispare a exception
}
