package br.senai.sp.cotia.ristorapp.ristorapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cotia.ristorapp.ristorapp.model.TipoRestaurante;

public interface TipoRepository extends PagingAndSortingRepository<TipoRestaurante, Long> {

}
