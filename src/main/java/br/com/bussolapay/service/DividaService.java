package br.com.bussolapay.service;

import br.com.bussolapay.config.exceptions.DividasException;
import br.com.bussolapay.infra.FactoryDividas;
import br.com.bussolapay.infra.FactoryRelatorios;
import br.com.bussolapay.model.*;
import br.com.bussolapay.model.enums.StatusDivida;
import br.com.bussolapay.repository.DividaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DividaService {

    private final ClienteService clienteService;
    private final DividaRepository dividaRepository;


    public void save(DividaCreate dividaCreate) {
        dividaRepository.saveAll(
                Optional.ofNullable(
                        FactoryDividas.generateDividas(dividaCreate, clienteService.getClienteLogado())
                ).orElseThrow(() -> new DividasException("Falha ao fabricar dividas"))
        );
    }

    public List<ResumoDiario> getResumosDiarios4Dias() {
        List<DividaDTO> dividas = dividaRepository.findDividaDTOByBetweenAndStatus(
                LocalDate.now(), LocalDate.now().plusDays(4L), StatusDivida.PENDENTE.name(), clienteService.getClienteLogado().getId()
        );


        return FactoryRelatorios.generateResumoPorDia(dividas, new RangeDate(LocalDate.now(), LocalDate.now().plusDays(4L)));
    }

    public List<ResumoDiario> getResumosDiariosPesonalizadoes(@Valid RangeDateAndFiltros range) {
        List<DividaDTO> dividas = new ArrayList<>();

        if (range.isPaga())
            dividas.addAll(dividaRepository.findDividaDTOByBetweenAndStatus(range.getDataInicio(), range.getDataFim(), StatusDivida.PAGA.name(), clienteService.getClienteLogado().getId()));
        if (range.isPendente())
            dividas.addAll(dividaRepository.findDividaDTOByBetweenAndStatus(range.getDataInicio(), range.getDataFim(), StatusDivida.PENDENTE.name(), clienteService.getClienteLogado().getId()));
        if (range.isVencida())
            dividas.addAll(dividaRepository.findDividaDTOByBetweenAndStatus(range.getDataInicio(), range.getDataFim(), StatusDivida.VENCIDA.name(), clienteService.getClienteLogado().getId()));

        return FactoryRelatorios.generateResumoTotal(dividas, range);
    }
}
