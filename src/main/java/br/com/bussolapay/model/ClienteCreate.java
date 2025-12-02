package br.com.bussolapay.model;

import br.com.bussolapay.common.Convercoes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Optional;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClienteCreate extends PersistentEntityModel {

    @NotNull(message = "Favor informar campo nome")
    @Pattern(regexp = "^[^<>]*$", message = "Nome contém caracteres inválidos")
    private String nome;

    @NotNull(message = "Favor informar campo data nascimento")
    private String dataNascimento;

    @CPF @Getter(AccessLevel.NONE)
    @NotNull(message = "Favor informar campo cpf")
    private String cpf;

    @Email
    @NotNull(message = "Favor informar campo email")
    @Pattern(regexp = "^[^<>]*$", message = "email contém caracteres inválidos")
    private String email;

    @Size(min = 5, message = "Senha precisa ter no mínimo 5 dígitos")
    @NotNull
    @Pattern(regexp = "^[^<>]*$", message = "senha contém caracteres inválidos")
    private String senha;
    @NotNull(message = "Favor informar campo confirmar senha")
    @NotNull
    @Pattern(regexp = "^[^<>]*$", message = "senha contém caracteres inválidos")
    private String confirmarSenha;


    public String getCpf() {
        return Convercoes.desformatarCPF(cpf);
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", confirmarSenha='" + confirmarSenha + '\'' +
                '}';
    }
}
