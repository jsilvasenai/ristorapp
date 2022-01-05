package br.senai.sp.cotia.ristorapp.ristorapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.senai.sp.cotia.ristorapp.ristorapp.util.HashUtil;
import lombok.Data;

@Data
@Entity
public class Administrador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	private String senha;
	
	public void setSenha(String senha) {
		this.senha = HashUtil.hash256(senha);
	}
	
	public void setSenhaHash(String senhaHash) {
		this.senha = senhaHash;
	}
}
