package br.senai.sp.cotia.ristorapp.ristorapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
	Usuario findByEmailAndSenha(String email, String senha);
}
