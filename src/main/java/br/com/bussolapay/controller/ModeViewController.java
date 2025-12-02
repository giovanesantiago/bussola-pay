package br.com.bussolapay.controller;

import br.com.bussolapay.model.ClienteCreate;
import br.com.bussolapay.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping() @Slf4j @RequiredArgsConstructor
public class ModeViewController {

    private final ClienteService clienteService;

    @GetMapping("/cadastrar/cliente")
    public ModelAndView viewCadastro() {
        ModelAndView mv = new ModelAndView("cadastro");
        mv.addObject("cliente", new ClienteCreate());
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView viewDashboard() {
        ModelAndView mv = new ModelAndView("dashboard");

        mv.addObject("data", Map.of(
                "nomeUsuario", clienteService.getClienteLogado().getNome()
        ));

        return mv;
    }



}

