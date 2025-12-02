package br.com.bussolapay.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor @Table(name = "divida") @Entity
public class Divida extends DividaBase{

    @Valid
    @OneToMany(mappedBy = "divida", cascade = CascadeType.ALL)
    private List<SubDivida> subDividas;

    @Valid
    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
