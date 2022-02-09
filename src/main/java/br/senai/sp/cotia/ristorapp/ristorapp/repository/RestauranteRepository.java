package br.senai.sp.cotia.ristorapp.ristorapp.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Restaurante;
import br.senai.sp.cotia.ristorapp.ristorapp.model.TipoRestaurante;

public interface RestauranteRepository extends PagingAndSortingRepository<Restaurante, Long> {
	public List<Restaurante> findByTipoId (Long idTipo);
}
