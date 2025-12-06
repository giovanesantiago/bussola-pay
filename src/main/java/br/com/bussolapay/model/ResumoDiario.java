package br.com.bussolapay.model;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ResumoDiario {
    private Integer qntDividas;
    private String semana;
    private String diaMes;
    private String valorTotal;
    private List<DividaDTO> dividas;
}
