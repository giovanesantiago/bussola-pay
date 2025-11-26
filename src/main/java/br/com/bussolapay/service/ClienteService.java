package br.com.bussolapay.service;


import br.com.bussolapay.config.exceptions.UsuarioException;
import br.com.bussolapay.config.security.AuthorizationService;
import br.com.bussolapay.model.Cliente;
import br.com.bussolapay.model.ClienteDTO;
import br.com.bussolapay.model.Usuario;
import br.com.bussolapay.model.enums.UserRole;
import br.com.bussolapay.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final AuthorizationService authorizationService;

    public void save(ClienteDTO clienteDTO) {
        Usuario usuario = new Usuario(clienteDTO.getEmail(), clienteDTO.getSenha(), UserRole.CLIENTE);
        if (!repository.existsByCpf(clienteDTO.getCpf()) && authorizationService.register(usuario).getFeito()) {
            repository.save(new Cliente(clienteDTO, usuario));
        } else {
            throw new UsuarioException(
                    repository.existsByCpf(clienteDTO.getCpf()) ? "Cpf ja cadastrado" : "Login invalido"
            );
        }
    }
}
