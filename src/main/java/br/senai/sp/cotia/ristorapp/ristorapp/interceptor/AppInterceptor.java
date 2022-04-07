package br.senai.sp.cotia.ristorapp.ristorapp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.cotia.ristorapp.ristorapp.annotation.Privado;
import br.senai.sp.cotia.ristorapp.ristorapp.annotation.Publico;
import br.senai.sp.cotia.ristorapp.ristorapp.controller.UsuarioController;
import br.senai.sp.cotia.ristorapp.ristorapp.restcontroller.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		HttpSession sessao = request.getSession();
		System.out.println(uri);

		
		// verifica se o handler é uma instância de HandlerMethod, o que indica
		// que foi encontrado o método associado à requisição
		if (handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			if (uri.equals("/")){
				return true;
			}
			
			if (uri.startsWith("/error")) {
				return true;
			}

			if (uri.startsWith("/rest")) {
				if (metodo.getMethodAnnotation(Privado.class) != null) {
					String token = null;
					try {
						token = request.getHeader("Authorization");
						Algorithm algorithm = Algorithm.HMAC256(UsuarioRestController.SECRET);
						JWTVerifier verifier = JWT.require(algorithm).withIssuer(UsuarioRestController.EMISSOR).build();
						DecodedJWT jwt = verifier.verify(token);
						Map<String, Claim> claims = jwt.getClaims();
						System.out.println("Nome:" + claims.get("nome_usuario"));
						return true;
					} catch (Exception e) {
						e.printStackTrace();
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						} else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}
				}
				return true;
			} else {
				if(metodo.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				
				if (sessao.getAttribute("user") != null) {
					return true;
				} else {
					response.sendRedirect("/");
					return false;
				}
			}
		}

		return true;
		
	}
}
