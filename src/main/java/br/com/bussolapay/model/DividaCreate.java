package br.com.bussolapay.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DividaCreate extends DividaCreateBase{

    @NotNull(message = "Favor informar campo tipo divida")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String tipoDivida;

    @NotNull(message = "Favor informar campo data vecimento")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String dataVecimento;

    @Valid
    public Set<SubDividaCreate> subDividas;

    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String subDividasJson;




}
