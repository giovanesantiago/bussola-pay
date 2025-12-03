package br.com.bussolapay.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DividaCreateBase {
    @NotNull(message = "Favor informar campo descrição")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    private String descricao;

    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    private String valor;

    @NotNull(message = "Favor informar campo Forma de Pagamento")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    private String formaDePagamento;

    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    private String parcelamento;

    public int getParcelamentoInt() {
        if(parcelamento != null ) {
            return Integer.parseInt(parcelamento);
        }
        return 0;
    }
}
