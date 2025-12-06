package br.com.bussolapay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DividaDTO {
    private Long id;
    private String descricao;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private String status;
    private String tipo;


    public DividaDTO(Number id, String descricao, Date dataVencimento, Number valor, String status, String tipo) {
        this.id = id.longValue();
        this.descricao = descricao;
        this.dataVencimento = dataVencimento.toLocalDate();
        this.valor = new BigDecimal(valor.toString());
        this.status = status;
        this.tipo = tipo;
    }
}
