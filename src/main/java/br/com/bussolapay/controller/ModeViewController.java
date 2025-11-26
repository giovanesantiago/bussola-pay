package br.com.bussolapay.controller;

import br.com.bussolapay.model.ClienteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mv")
public class ModeViewController {

    @GetMapping("/cadastrar/cliente")
    public ModelAndView viewCadastro() {
        ModelAndView mv = new ModelAndView("/cadastro");
        mv.addObject("cliente", new ClienteDTO());
        return mv;
    }

/*TODO: Criar pdf com Termos de Uso*/
}

