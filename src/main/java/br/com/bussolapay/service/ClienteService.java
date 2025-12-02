package br.com.bussolapay.service;


import br.com.bussolapay.config.exceptions.NotFoundException;
import br.com.bussolapay.config.exceptions.UsuarioException;
import br.com.bussolapay.config.security.AuthorizationService;
import br.com.bussolapay.model.Cliente;
import br.com.bussolapay.model.ClienteCreate;
import br.com.bussolapay.model.ClienteDTO;
import br.com.bussolapay.model.Usuario;
import br.com.bussolapay.model.enums.UserRole;
import br.com.bussolapay.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final AuthorizationService authorizationService;

    public void save(ClienteCreate clienteCreate) {
        Usuario usuario = new Usuario(clienteCreate.getEmail(), clienteCreate.getSenha(), UserRole.CLIENTE);
        if (!repository.existsByCpf(clienteCreate.getCpf()) && authorizationService.register(usuario).getFeito()) {
            repository.save(new Cliente(clienteCreate, usuario));
        } else {
            throw new UsuarioException(
                    repository.existsByCpf(clienteCreate.getCpf()) ? "Cpf ja cadastrado" : "Login invalido"
            );
        }
    }

    public ClienteDTO getClienteLogado() {
        Optional<Cliente> clienteOptional = repository.findClienteByUsuario_Login(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );

        return new ClienteDTO(clienteOptional.orElseThrow(NotFoundException::new));
    }
}
