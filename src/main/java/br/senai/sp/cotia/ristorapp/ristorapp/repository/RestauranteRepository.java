package br.senai.sp.cotia.ristorapp.ristorapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Restaurante;

public interface RestauranteRepository extends PagingAndSortingRepository<Restaurante, Long> {
	
}
