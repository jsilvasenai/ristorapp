package br.senai.sp.cotia.ristorapp.ristorapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.senai.sp.cotia.ristorapp.ristorapp.util.HashUtil;
import lombok.Data;

@Data
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	@JsonProperty(access =  Access.WRITE_ONLY)
	private String senha;

	public void setSenha(String senha) {
		this.senha = HashUtil.hash256(senha);
	}

	public void setSenhaHash(String senhaHash) {
		this.senha = senhaHash;
	}
}
