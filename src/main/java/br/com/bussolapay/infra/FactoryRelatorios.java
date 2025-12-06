package br.com.bussolapay.infra;

import br.com.bussolapay.model.DividaDTO;
import br.com.bussolapay.model.RangeDate;
import br.com.bussolapay.model.ResumoDiario;
import br.com.bussolapay.model.enums.StatusDivida;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class FactoryRelatorios {


    public static List<ResumoDiario> generateResumoPorDia(List<DividaDTO> dividas, RangeDate rangeDate) {

        return rangeDate.getDataInicio().datesUntil(rangeDate.getDataFim())
                .map(data -> {
                    List<DividaDTO> dividasDoDia = Optional.ofNullable(dividas)
                            .orElse(Collections.emptyList())
                            .stream()
                            .filter(divida -> divida.getDataVencimento().equals(data))
                            .collect(Collectors.toList());

                    String diaSemana = "";
                    if (data.equals(LocalDate.now())) {
                        diaSemana = "HOJE";
                    } else if (data.equals(LocalDate.now().plusDays(1))) {
                        diaSemana = "AMANHA";
                    }
                    return ResumoDiario.builder()
                            .qntDividas(dividasDoDia.size())
                            .semana(
                                    diaSemana.isEmpty()
                                            ? data.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                                            : diaSemana
                            )
                            .diaMes(data.format(DateTimeFormatter.ofPattern("dd/MM")))
                            .valorTotal(dividasDoDia.isEmpty() ? "R$ 0,00" : calcularValorTotalComSubDividas(dividasDoDia))
                            .valorTotalAPagar(dividasDoDia.isEmpty() ? "R$ 0,00" : calcularValorTotalComSubDividasPedente(dividasDoDia))
                            .dividas(dividasDoDia)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private static String calcularValorTotalComSubDividas(List<DividaDTO> dividas) {
        BigDecimal total = dividas.stream()
                .map(DividaDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(total);
    }

    private static String calcularValorTotalComSubDividasPedente(List<DividaDTO> dividas) {
        BigDecimal total = dividas.stream()
                .filter(e -> e.getStatus().equals(StatusDivida.PENDENTE.name()))
                .map(DividaDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(total);
    }

    public static List<ResumoDiario> generateResumoTotal(List<DividaDTO> dividas, RangeDate rangeDate) {

        return List.of(
                ResumoDiario.builder()
                        .qntDividas(dividas.size())
                        .semana("Relatorio Personalizado")
                        .diaMes(rangeDate.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM")) + " - " + rangeDate.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM")))
                        .valorTotal(dividas.isEmpty() ? "R$ 0,00" : calcularValorTotalComSubDividas(dividas))
                        .valorTotalAPagar(dividas.isEmpty() ? "R$ 0,00" : calcularValorTotalComSubDividasPedente(dividas))
                        .dividas(dividas)
                        .build()
        );

    }
}
