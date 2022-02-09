package br.senai.sp.cotia.ristorapp.ristorapp.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {
	public List<Avaliacao> findByRestauranteId(Long idRestaurante);
}
