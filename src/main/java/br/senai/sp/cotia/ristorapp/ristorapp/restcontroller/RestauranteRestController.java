package br.senai.sp.cotia.ristorapp.ristorapp.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Restaurante;
import br.senai.sp.cotia.ristorapp.ristorapp.model.TipoRestaurante;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.RestauranteRepository;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.TipoRepository;

@RequestMapping("/rest")
@RestController
public class RestauranteRestController {
	@Autowired
	RestauranteRepository repository;
	@Autowired
	TipoRepository tipoRepository;

	@RequestMapping(value = "/restaurante", method = RequestMethod.GET)
	public Iterable<Restaurante> getRestaurantes() {
		System.out.println("passou aqui");
		return repository.findAll();
	}

	@RequestMapping(value = "/restaurante/{id}")
	public ResponseEntity<Restaurante> findRestaurante(@PathVariable("id") Long idRestaurante) {
		Optional<Restaurante> restaurante = repository.findById(idRestaurante);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/restaurante/tipo/{idTipo}")
	public List<Restaurante> findByTipo(@PathVariable("idTipo") Long idTipo) {
		List<Restaurante> restaurantes = repository.findByTipoId(idTipo);
		return restaurantes;
	}

}
