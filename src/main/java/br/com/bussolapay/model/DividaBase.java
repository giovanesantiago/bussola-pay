package br.com.bussolapay.model;

import br.com.bussolapay.model.enums.StatusDivida;
import br.com.bussolapay.model.enums.TipoDivida;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass @Getter @Setter @AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class DividaBase extends PersistentEntityModel{

    @NotNull(message = "Favor informar campo descrição")
    private String descricao;

    @NotNull(message = "Favor informar campo tipo valor")
    private BigDecimal valor;

    private Integer parcelamento;
    private Integer posicaoParcelamento;

    @NotNull(message = "Favor informar campo data vencimento")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @NotNull(message = "Favor informar campo tipo divida")
    @Column(name = "tipo_divida") @Enumerated(EnumType.STRING)
    private TipoDivida tipoDivida;

    @Column(name = "status") @Enumerated(EnumType.STRING)
    private StatusDivida status;


}
