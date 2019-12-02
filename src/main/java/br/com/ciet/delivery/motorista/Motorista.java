package br.com.ciet.delivery.motorista;

import br.com.ciet.delivery.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity(name="Motoristas")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Motorista {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="ativo")
	private Boolean ativo;

	@Column(name="bloqueado")
	private Boolean bloqueado;

	@Column(name="cnh")
	@JsonIgnore
	private String cnh;

	@Column(name="cpf_cnpj",nullable=false)
	@JsonIgnore
	private String cpfCnpj;

	@OneToMany(mappedBy="motorista", cascade = CascadeType.ALL)
	private List<Veiculo> veiculos;


}
