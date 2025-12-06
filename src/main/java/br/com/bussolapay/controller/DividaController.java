package br.com.bussolapay.controller;

import br.com.bussolapay.config.exceptions.DividasException;
import br.com.bussolapay.model.DividaCreate;
import br.com.bussolapay.model.RangeDateAndFiltros;
import br.com.bussolapay.service.ClienteService;
import br.com.bussolapay.service.DividaService;
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
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/divida") @RequiredArgsConstructor
public class DividaController {

    private final DividaService dividaService;
    private final ModeViewController modeViewController;
    private final ClienteService clienteService;

    @PostMapping() @SuppressWarnings(value = "XSSVulnerability")
    public ModelAndView newDivida(@ModelAttribute("divida") @Valid DividaCreate dividaCreate, BindingResult result){
        ModelAndView mv = new ModelAndView("dashboard");

        if(result.hasErrors()){ //TODO: verificar se esta exibindo retorno de erros no front
            List<String> erros =
                    Optional.of(result.getAllErrors()).orElse(Collections.emptyList())
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

            mv.setViewName("adicionar-divida");
            mv.addObject("divida", dividaCreate)
                    .addObject("erros", erros);

            return mv;
        }

        try {
            dividaService.save(dividaCreate);
            // TODO: ativa mensagem de criado com sucesso
        } catch (DividasException usuarioException){
            mv.setViewName("adicionar-divida");
            mv.addObject("divida", dividaCreate)
                    .addObject("erros", List.of(usuarioException.getMessage()));
        }

        return modeViewController.viewDashboard();
    }

    @PostMapping("/filtrar")
    public ModelAndView listar(@ModelAttribute("range") @Valid RangeDateAndFiltros range){
        ModelAndView mv = new ModelAndView("dashboard");

        mv.addObject("data", Map.of(
                "nomeUser", clienteService.getClienteDTOLogado().getNome(),
                "resumoDiario", dividaService.getResumosDiariosPesonalizadoes(range)
        ));
        mv.addObject("range", new RangeDateAndFiltros());

        return mv;
    }
}
