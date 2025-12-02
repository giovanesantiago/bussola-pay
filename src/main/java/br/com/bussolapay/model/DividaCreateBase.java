package br.com.bussolapay.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DividaCreateBase {
    @NotNull(message = "Favor informar campo descrição")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String descricao;

    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String valor;

    @NotNull(message = "Favor informar campo Forma de Pagamento")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String formaDePagamento;

    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    public String parcelamento;
}
