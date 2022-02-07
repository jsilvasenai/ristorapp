package br.senai.sp.cotia.ristorapp.ristorapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		if (uri.startsWith("rest")) {

		} else {
			// libera os arquivos de estilo e js e a página inicial
			if (uri.equals("/") || uri.contains(".css") || uri.contains(".js")) {
				return true;
			}
		}
		return false;
	}
}
