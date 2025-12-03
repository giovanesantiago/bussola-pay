package br.com.bussolapay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor @SuperBuilder
@Table(name = "sub_divida") @Entity
public class SubDivida extends DividaBase{

    @ManyToOne
    @JoinColumn(name = "divida_id")
    private Divida divida;
}
