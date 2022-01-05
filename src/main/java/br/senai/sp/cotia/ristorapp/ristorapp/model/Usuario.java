package br.senai.sp.cotia.ristorapp.ristorapp.model;

import lombok.Data;

@Data
public class Usuario {
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String foto;
}
