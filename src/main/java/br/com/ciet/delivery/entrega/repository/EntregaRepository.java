package br.com.ciet.delivery.entrega.repository;


import br.com.ciet.delivery.entrega.model.Entrega;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EntregaRepository extends CrudRepository<Entrega, Long> {

  <T extends Entrega> T save(T entrega);
}
