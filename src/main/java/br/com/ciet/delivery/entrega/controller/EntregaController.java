package br.com.ciet.delivery.entrega.controller;


import br.com.ciet.delivery.entrega.service.EntregaService;
import br.com.ciet.delivery.entrega.model.Entrega;
import br.com.ciet.delivery.util.RestApiURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(RestApiURI.ENTREGA_PACOTE)
public class EntregaController {

  private EntregaService entregaService;

  @Autowired
  public EntregaController(EntregaService viagemService) {
    this.entregaService = viagemService;
  }

  @GetMapping("{deliveryId}/step")
  @ResponseStatus(code = HttpStatus.OK)
  public Entrega buscaEntregasViagem(@PathVariable("deliveryId") Long deliveryId){
    return this.entregaService.buscaEntregas(deliveryId);
  }

  @PostMapping
  @ResponseStatus(code=HttpStatus.OK)
  @ResponseBody
  public Entrega cadastraEntregas(@Valid @RequestBody Entrega entrega) {
    return this.entregaService.novaEntrega(entrega);
  }

}
