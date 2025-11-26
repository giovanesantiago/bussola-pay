package br.com.bussolapay.config.security;

import br.com.bussolapay.model.Usuario;
import br.com.bussolapay.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UsuarioRepository reporitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return reporitory.findByLogin(username);
    }


    public ResponseAuthorization register(Usuario user){
        if(this.reporitory.existsByLogin(user.getUsername())) return ResponseAuthorization.builder().feito(false).message("Login invalido").build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

        Usuario newUser = user;
        newUser.setPassword(encryptedPassword);

        reporitory.save(newUser);
        return ResponseAuthorization.builder().feito(true).message(" ").build();
    }
}
