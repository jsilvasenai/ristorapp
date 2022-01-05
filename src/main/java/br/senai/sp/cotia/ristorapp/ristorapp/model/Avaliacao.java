package br.senai.sp.cotia.ristorapp.ristorapp.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Avaliacao {
	private Long id;
	private Restaurante restaurante;
	private LocalDate dataVisita;
	private String comentario;
	private Double nota;

}
