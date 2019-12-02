package br.com.ciet.delivery.carga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="Cargas")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Carga {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @Column(name="peso")
  private Long peso;


}
