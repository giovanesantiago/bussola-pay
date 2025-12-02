package br.com.bussolapay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClienteDTO {
    private String nome;
    private String dataNascimento;
    private String cpf;

    public ClienteDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.dataNascimento = cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.cpf = cliente.getCpfFormatado();
    }
}
