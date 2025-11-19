package br.com.bussolapay.controller;

import br.com.bussolapay.config.exceptions.UsuarioException;
import br.com.bussolapay.model.ClienteDTO;
import br.com.bussolapay.repository.UsuarioRepository;
import br.com.bussolapay.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller @Slf4j
@RequestMapping(value = "/cliente") @RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping() @SuppressWarnings(value = "XSSVulnerability")
    public ModelAndView newCliente(@ModelAttribute("cliente") @Valid ClienteDTO clienteDTO, BindingResult result){
        ModelAndView mv = new ModelAndView("/login");

        if(result.hasErrors()){
            List<String> erros =
                    Optional.ofNullable(result.getAllErrors()).orElse(Collections.emptyList())
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

            mv.setViewName("/cadastro");
            mv.addObject("cliente", clienteDTO);
            mv.addObject("erros", erros);
        }

        try {
            clienteService.save(clienteDTO);
        } catch (UsuarioException usuarioException){
            mv.setViewName("/cadastro");
            mv.addObject("cliente", clienteDTO);
            mv.addObject("erros", List.of(usuarioException.getMessage()));
        }

        return mv;
    }

}
