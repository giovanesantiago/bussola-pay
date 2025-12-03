package br.com.bussolapay.infra;

import br.com.bussolapay.common.Convercoes;
import br.com.bussolapay.model.*;
import br.com.bussolapay.model.enums.TipoDivida;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FactoryDividas {

    public static List<Divida> generateDividas(DividaCreate dividaCreate, Cliente cliente) {
        switch (TipoDivida.fromDividaCreate(dividaCreate)) {
            case COMPOSTA -> {
                return generateDividaByComposta(dividaCreate, cliente);
            }
            case SIMPLES_A_VISTA -> {
                return generateDividaBySimplesAvista(dividaCreate, cliente);
            }
            case SIMPLES_PARCELADA -> {
                return generateDividaBySimplesParcelada(dividaCreate, cliente);
            }
            default -> throw new IllegalArgumentException("Nenhum tipo de divida encontrado");
        }
    }

    private static List<Divida> generateDividaBySimplesAvista(DividaCreate dividaCreate, Cliente cliente) {

        return List.of(
                Divida.builder()
                        .descricao(dividaCreate.getDescricao())
                        .tipoDivida(TipoDivida.SIMPLES_A_VISTA)
                        .valor(new BigDecimal(
                                Convercoes.realComCifraoToDoubleWithCommaFinal(dividaCreate.getValor()))
                        )
                        .dataVencimento(LocalDate.parse(dividaCreate.getDataVecimento()))
                        .cliente(cliente)
                        .build()
        );
    }

    private static List<Divida> generateDividaBySimplesParcelada(DividaCreate dividaCreate, Cliente cliente) {
        List<Divida> retorno = new ArrayList<>();

        int cont = 1;
        do {
            retorno.add(
                    Divida.builder()
                            .descricao(dividaCreate.getDescricao())
                            .tipoDivida(TipoDivida.SIMPLES_PARCELADA)
                            .valor(new BigDecimal(
                                    Convercoes.realComCifraoToDoubleWithCommaFinal(dividaCreate.getValor()))
                            )
                            .parcelamento(dividaCreate.getParcelamentoInt())
                            .posicaoParcelamento(cont)
                            .recorrente(dividaCreate.getParcelamentoInt() == 0)
                            .dataVencimento(LocalDate.parse(dividaCreate.getDataVecimento()).plusMonths(cont - 1))
                            .cliente(cliente)
                            .build()
            );
            cont++;
        } while (cont <= dividaCreate.getParcelamentoInt());

        return retorno;
    }

    private static List<Divida> generateDividaByComposta(DividaCreate dividaCreate, Cliente cliente) {
        Divida retorno = Divida.builder()
                .descricao(dividaCreate.getDescricao())
                .tipoDivida(TipoDivida.COMPOSTA)
                .dataVencimento(LocalDate.parse(dividaCreate.getDataVecimento()))
                .subDividas(new ArrayList<>())
                .cliente(cliente)
                .build();


        Optional.ofNullable(dividaCreate.getSubDividas())
                .orElse(Collections.emptyList())
                .forEach(
                        subDividaCreate -> {
                            retorno.getSubDividas().addAll(
                                    subDividaCreate.getFormaDePagamento().equalsIgnoreCase("A_VISTA")
                                            ? generateSubDividaByAvista(subDividaCreate, retorno)
                                            : generateSubDividaByParcelada(subDividaCreate, retorno)
                            );
                        }
                );

        retorno.setValor(
                retorno.getSubDividas().stream()
                        .map(SubDivida::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return List.of(retorno);
    }


    private static List<SubDivida> generateSubDividaByAvista(SubDividaCreate subDividaCreate, Divida divida) {

        return List.of(
                SubDivida.builder()
                        .descricao(subDividaCreate.getDescricao())
                        .tipoDivida(TipoDivida.SUB_DIVIDA_A_VISTA)
                        .valor(new BigDecimal(
                                Convercoes.realComCifraoToDoubleWithCommaFinal(subDividaCreate.getValor()))
                        )
                        .dataVencimento(divida.getDataVencimento())
                        .divida(divida)
                        .build()
        );
    }

    private static List<SubDivida> generateSubDividaByParcelada(SubDividaCreate subDividaCreate, Divida divida) {
        List<SubDivida> retorno = new ArrayList<>();

        int cont = 1;
        do {
            retorno.add(
                    SubDivida.builder()
                            .descricao(subDividaCreate.getDescricao())
                            .tipoDivida(TipoDivida.SUB_DIVIDA_PARCELADA)
                            .valor(new BigDecimal(
                                    Convercoes.realComCifraoToDoubleWithCommaFinal(subDividaCreate.getValor()))
                            )
                            .parcelamento(subDividaCreate.getParcelamentoInt())
                            .posicaoParcelamento(cont)
                            .recorrente(subDividaCreate.getParcelamentoInt() == 0)
                            .dataVencimento(divida.getDataVencimento().plusMonths(cont - 1))
                            .divida(divida)
                            .build()
            );
            cont++;
        } while (cont <= subDividaCreate.getParcelamentoInt());

        return retorno;
    }

}
