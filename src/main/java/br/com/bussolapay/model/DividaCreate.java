package br.com.bussolapay.model;


import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DividaCreate extends DividaCreateBase {

    @NotNull(message = "Favor informar campo tipo divida")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String tipoDivida;

    @NotNull(message = "Favor informar campo data vecimento")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String dataVecimento;

    @Valid
    @Getter(AccessLevel.NONE)
    public List<SubDividaCreate> subDividas;

    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String subDividasJson;

    public List<SubDividaCreate> getSubDividas() {
        if (subDividas != null && !subDividas.isEmpty()) return subDividas;

       SubDividaCreate[] subDividaCreatesArrays = new Gson().fromJson(subDividasJson, SubDividaCreate[].class);

       subDividas = List.of(subDividaCreatesArrays);
        return subDividas;
    }
}
