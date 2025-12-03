package br.com.bussolapay.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor @SuperBuilder
@NoArgsConstructor @Table(name = "divida") @Entity
public class Divida extends DividaBase{

    @Valid
    @OneToMany(mappedBy = "divida", cascade = CascadeType.ALL)
    private List<SubDivida> subDividas;

    @Valid
    @ManyToOne() @NotNull
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


}
