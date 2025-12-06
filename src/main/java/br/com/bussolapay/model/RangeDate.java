package br.com.bussolapay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class RangeDate {
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
