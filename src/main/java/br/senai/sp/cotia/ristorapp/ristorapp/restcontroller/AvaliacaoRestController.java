package br.senai.sp.cotia.ristorapp.ristorapp.restcontroller;

import java.net.URI;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Avaliacao;
import br.senai.sp.cotia.ristorapp.ristorapp.model.Restaurante;
import br.senai.sp.cotia.ristorapp.ristorapp.model.Usuario;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.AvaliacaoRepository;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.RestauranteRepository;

@RestController
@RequestMapping("/rest")
public class AvaliacaoRestController {

	@Autowired
	private AvaliacaoRepository repository;

	@Autowired
	private RestauranteRepository restRep;

	@RequestMapping(value = "/private/avaliacao", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarUsuario(@RequestBody Avaliacao avaliacao) {
		try {
			repository.save(avaliacao);
			return ResponseEntity.created(URI.create("/avaliacao/" + avaliacao.getId())).body(avaliacao);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Avaliacao>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Avaliacao>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/avaliacao/restaurante/{idRestaurante}", method = RequestMethod.GET)
	public Iterable<Avaliacao> getAvaliacoes(@PathVariable("idRestaurante") Long idRestaurante) {
		return repository.findByRestauranteId(idRestaurante);
	}

}
