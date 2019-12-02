package br.com.ciet.delivery.exception;

import br.com.ciet.delivery.exception.NotFoundException;

public class EntregaNaoEncontradaException extends NotFoundException {

  public EntregaNaoEncontradaException() {
    super("Entrega não encontrada!");
  }
}
