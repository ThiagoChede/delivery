package br.com.ciet.delivery.entrega.service;

import br.com.ciet.delivery.entrega.repository.EntregaRepository;
import br.com.ciet.delivery.entrega.model.Entrega;
import br.com.ciet.delivery.exception.EntregaNaoEncontradaException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
public class EntregaService {

  private EntregaRepository entregaRepository;

  public EntregaService(EntregaRepository entregaRepository) {
    this.entregaRepository = entregaRepository;
  }


  public Entrega buscaEntregas(Long deliveryId) {

    return this.entregaRepository.findById(deliveryId).orElseThrow(EntregaNaoEncontradaException::new);


  }


  public Entrega novaEntrega(final @Valid Entrega entrega) {

    entrega.setCarga(entrega.getCarga());
    entrega.setVeiculo(entrega.getVeiculo());

    this.entregaRepository.save(entrega);

    return entrega;
  }


}
