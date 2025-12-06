package br.com.bussolapay.controller;

import br.com.bussolapay.model.ClienteCreate;
import br.com.bussolapay.model.DividaCreate;
import br.com.bussolapay.service.ClienteService;
import br.com.bussolapay.service.DividaService;
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
    private final DividaService dividaService;

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
                "nomeUser", clienteService.getClienteDTOLogado().getNome(),
                "resumoDiario", dividaService.getResumosDiarios5Dias()
        ));

        return mv;
    }

    @GetMapping("/adicionar/divida")
    public ModelAndView viewCadastroDivida() {
        ModelAndView mv = new ModelAndView("adicionar-divida");
        mv.addObject("divida", new DividaCreate());
        return mv;
    }



}

