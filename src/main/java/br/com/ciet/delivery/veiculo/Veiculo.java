package br.com.ciet.delivery.veiculo;

import br.com.ciet.delivery.motorista.Motorista;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="veiculos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Veiculo {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@ManyToOne
	@JoinColumn(name="id_motorista")
	@JsonIgnore
	private Motorista motorista;

	@Column(name="descricao",nullable=false)
	private String descricao;

	@Column(name="placa")
	private String placa;

	@Column(name="renavam")
	private String renavam;

	@Column(name="marca")
	private String marca;

	@Column(name="modelo")
	private String modelo;

	@Column(name="ano")
	private String ano;

	@Column(name="cor")
	private String cor;

}
