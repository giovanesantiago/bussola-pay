package br.com.bussolapay.repository;

import br.com.bussolapay.model.Cliente;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(@CPF String cpf);

    Optional<Cliente> findClienteByUsuario_Login(String usuario);
}
