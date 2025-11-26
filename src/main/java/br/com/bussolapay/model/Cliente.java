package br.com.bussolapay.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity @Table(name = "cliente") @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Cliente extends PersistentEntityModel {

    @NotNull(message = "informe o campo nome")
    @Column(length = 100)
    private String nome;

    @NotNull(message = "informe o campo data nascimento")
    private LocalDate dataNascimento;

    @NotNull(message = "informe o campo CPF")
    @CPF(message = "Infome um CPF valido")
    @Column(length = 14, unique = true)
    private String cpf;

    @Valid
    @OneToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cliente(ClienteDTO clienteDTO, Usuario usuario) {
        this.nome = clienteDTO.getNome();
        this.dataNascimento = LocalDate.parse(clienteDTO.getDataNascimento());
        this.cpf = clienteDTO.getCpf();
        this.usuario = usuario;
    }
}
