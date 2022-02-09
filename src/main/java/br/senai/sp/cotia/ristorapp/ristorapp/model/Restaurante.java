package br.senai.sp.cotia.ristorapp.ristorapp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Restaurante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "text")
	private String descricao;
	private String cep;
	private String endereco;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	@Column(columnDefinition = "text")
	private String fotos;
	@ManyToOne
	private TipoRestaurante tipo;
	private String site;
	private String telefone;
	@OneToMany(mappedBy = "restaurante")
	private List<Avaliacao> avaliacoes;

	public String[] getVetorFotos() {
		return this.fotos.split(";");
	}
}