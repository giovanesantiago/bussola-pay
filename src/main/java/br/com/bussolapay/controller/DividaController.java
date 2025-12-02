package br.com.bussolapay.controller;

import br.com.bussolapay.model.DividaCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping(value = "/divida") @RequiredArgsConstructor
public class DividaController {


    @PostMapping() @SuppressWarnings(value = "XSSVulnerability")
    public ModelAndView newCliente(@ModelAttribute("divida") @Valid DividaCreate dividaCreate, BindingResult result){
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("data", Map.of(
                "nomeUsuario", "TESTE"
        ));
        return mv;
    }
}
