package br.com.bussolapay.model;

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

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DividaBase extends PersistentEntityModel{
    @NotNull(message = "Favor informar campo descrição")
    public String descricao;

    @NotNull(message = "Favor informar campo tipo divida")
    @Column(name = "tipo_divida") @Enumerated(EnumType.STRING)
    public TipoDivida tipoDivida;

    @NotNull(message = "Favor informar campo tipo valor")
    public BigDecimal valor;

    public Integer parcelamento;
    public Integer posicaoParcelamento;
    public Boolean recorrente;

    @NotNull(message = "Favor informar campo data vecimento")
    @Column(name = "data_vecimento")
    public LocalDate dataVecimento;
}
