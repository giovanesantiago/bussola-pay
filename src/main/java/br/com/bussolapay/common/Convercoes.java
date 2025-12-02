package br.com.bussolapay.common;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convercoes {

    public static String formatCPF(String cpf) {
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
        Matcher matcher = pattern.matcher(cpf);

        if (matcher.matches()) cpf = matcher.replaceAll("$1.$2.$3-$4");

        return cpf;
    }

    public static String desformatarCPF(String cpf) {
        return Optional.ofNullable(cpf).orElse("").replaceAll("\\D", "");
    }

}
