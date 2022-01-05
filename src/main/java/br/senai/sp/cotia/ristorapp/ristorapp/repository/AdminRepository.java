package br.senai.sp.cotia.ristorapp.ristorapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {
	
}
