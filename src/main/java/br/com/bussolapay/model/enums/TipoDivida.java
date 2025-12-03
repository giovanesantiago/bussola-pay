package br.com.bussolapay.model.enums;

import br.com.bussolapay.model.DividaCreate;

public enum TipoDivida {
    COMPOSTA,
    SIMPLES_A_VISTA,
    SIMPLES_PARCELADA,
    SUB_DIVIDA_PARCELADA,
    SUB_DIVIDA_A_VISTA;

    public static TipoDivida fromDividaCreate(DividaCreate dividaCreate) {
        if(equalTxt(dividaCreate.getTipoDivida(), "composta") && equalTxt(dividaCreate.getFormaDePagamento(), "vista"))
            return TipoDivida.COMPOSTA;
        if(equalTxt(dividaCreate.getTipoDivida(), "simples") && equalTxt(dividaCreate.getFormaDePagamento(), "vista"))
            return TipoDivida.SIMPLES_A_VISTA;
        if(equalTxt(dividaCreate.getTipoDivida(), "simples") && equalTxt(dividaCreate.getFormaDePagamento(), "parcelada"))
            return TipoDivida.SIMPLES_PARCELADA;

        throw new IllegalArgumentException("Nenhum tipo  encontrado");
    }

    private static boolean equalTxt(String texto1, String texto2) {
        return texto1.equalsIgnoreCase(texto2);
    }
}
