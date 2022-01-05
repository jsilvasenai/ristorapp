package br.senai.sp.cotia.ristorapp.ristorapp.model;

import lombok.Data;

@Data
public class Restaurante {
	private Long id;
	private String nome;
	private String descricao;
	private String cep;
	private String endereco;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String fotos;
	private TipoRestaurante tipo;
	private String site;
	private String telefone;
}
