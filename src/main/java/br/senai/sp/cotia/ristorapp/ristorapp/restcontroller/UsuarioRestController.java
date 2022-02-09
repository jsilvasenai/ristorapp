package br.senai.sp.cotia.ristorapp.ristorapp.restcontroller;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.senai.sp.cotia.ristorapp.ristorapp.model.TokenJWT;
import br.senai.sp.cotia.ristorapp.ristorapp.model.Usuario;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.UsuarioRepository;

@RestController
@RequestMapping("/rest")
public class UsuarioRestController {

	public static final String EMISSOR = "SENAI";
	public static final String SECRET = "Ristor@pp";

	@Autowired
	private UsuarioRepository repository;

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
		try {
			repository.save(usuario);
			return ResponseEntity.created(URI.create("/usuario/" + usuario.getId())).body(usuario);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenJWT> logar(@RequestBody Usuario usuario) {
		usuario = repository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if (usuario != null) {
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("id_usuario", usuario.getId());
			claims.put("nome_usuario", usuario.getNome());
			// hora atual em segundos
			Calendar expiracao = Calendar.getInstance();
			expiracao.add(Calendar.HOUR, 1);
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			TokenJWT tokenJwt = new TokenJWT();
			tokenJwt.setToken(JWT.create().withPayload(claims).withIssuer(EMISSOR)
					.withExpiresAt(expiracao.getTime()).sign(algorithm));
			return ResponseEntity.ok(tokenJwt);
		} else {
			return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
		}
	}
}
