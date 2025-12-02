package br.com.bussolapay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sub_divida") @Entity
public class SubDivida extends DividaBase{

    @ManyToOne
    @JoinColumn(name = "divida_id")
    private Divida divida;
}
