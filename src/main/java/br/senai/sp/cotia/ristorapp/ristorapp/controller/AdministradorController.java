package br.senai.sp.cotia.ristorapp.ristorapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cotia.ristorapp.ristorapp.annotation.Publico;
import br.senai.sp.cotia.ristorapp.ristorapp.model.Administrador;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.AdminRepository;
import br.senai.sp.cotia.ristorapp.ristorapp.util.HashUtil;

@Controller
public class AdministradorController {

	@Autowired
	private AdminRepository admRep;

	@RequestMapping("formAdministrador")
	public String form() {
		return "administrador/form";
	}

	@RequestMapping("salvarAdm")
	public String salvarAdm(Administrador admin, RedirectAttributes attributes) {
		boolean alteracao = admin.getId() != null ? true : false;
		// caso seja um cadastro novo e não tenha sido informada a senha, a senha será o
		// primeiro nome
		if (admin.getSenha().equals(HashUtil.hash256(""))) {
			if (!alteracao) {
				admin.setSenha(admin.getNome().substring(0, admin.getNome().indexOf(" ")));
			} else {
				// caso seja alteração, mantém a mesma senha
				String senha = admRep.findById(admin.getId()).get().getSenha();
				admin.setSenhaHash(senha);
			}
		}
		try {
			admRep.save(admin);
			attributes.addFlashAttribute("mensagemSucesso",
					"Administrador salvo com sucesso. Caso seja uma inserção e a senha não foi informada, a mesma será o primeiro nome conforme foi cadastrado");
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagemErro", "Erro ao realizar o cadastro: " + e.getMessage());
		}
		if (alteracao) {
			return "redirect:/listarAdm/1";
		} else {
			return "redirect:/formAdministrador";
		}
	}

	@RequestMapping("/listarAdm/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// caso queira ordenar por algum campo, acrescenta-se o Sort.by no 3º parâmetro
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Administrador> tipoPage = admRep.findAll(pageable);
		int totalPages = tipoPage.getTotalPages();
		model.addAttribute("admins", tipoPage.getContent());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		model.addAttribute("pageNumbers", pageNumbers);
		return "administrador/lista";
	}

	@RequestMapping("/alterarAdm")
	public String alterarTipo(Model model, Long idAdm) {
		Administrador admin = admRep.findById(idAdm).get();
		model.addAttribute("adm", admin);
		return "forward:/formAdministrador";
	}

	@RequestMapping("/excluirAdm")
	public String excluirTipo(Long idAdm) {
		admRep.deleteById(idAdm);
		return "redirect:/listarAdm/1";
	}

	@Publico
	@RequestMapping("/login")
	public String login(Administrador admLogin, RedirectAttributes attributes, HttpSession session) {
		Administrador admin = admRep.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		if (admin != null) {
			session.setAttribute("user", admin);
			return "redirect:/listarRestaurante/1";
		} else {
			attributes.addFlashAttribute("mensagemErro", "Login e/ou senha inválidos");
			return "redirect:/";
		}

	}

	@RequestMapping("/logout")
	public String login(HttpSession session) {
		session.invalidate();

		return "redirect:/";

	}
}
