package br.com.bussolapay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter @NoArgsConstructor
public class RangeDateAndFiltros extends RangeDate{
    private boolean pendente;
    private boolean paga;
    private boolean vencida;

    public RangeDateAndFiltros(LocalDate dataInicio, LocalDate dataFim, boolean pendente, boolean paga, boolean vencida) {
        super(dataInicio, dataFim);
        this.pendente = pendente;
        this.paga = paga;
        this.vencida = vencida;
    }

    public RangeDateAndFiltros(LocalDate dataInicio, LocalDate dataFim) {
        super(dataInicio, dataFim);
    }
}
