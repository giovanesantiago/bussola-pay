package br.com.bussolapay.config.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseAuthorization {

    private Boolean feito;
    private String message;


}
