package br.com.ciet.delivery.entrega.model;

import br.com.ciet.delivery.veiculo.Veiculo;
import br.com.ciet.delivery.carga.Carga;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name="Entregas")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Entrega {

  @Id
  @GeneratedValue
  @Column(name="id")
  private Long  id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="id_carga")
  private Carga carga;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="id_veiculo")
  private Veiculo veiculo;
}

