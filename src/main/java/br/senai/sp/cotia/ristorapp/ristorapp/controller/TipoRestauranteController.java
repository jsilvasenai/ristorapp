package br.senai.sp.cotia.ristorapp.ristorapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cotia.ristorapp.ristorapp.model.TipoRestaurante;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.TipoRepository;

@Controller
public class TipoRestauranteController {

	@Autowired
	private TipoRepository tipoRep;

	@RequestMapping("/formTipo")
	public String form() {
		return "tipoRestaurante/form";
	}

	@RequestMapping(value = "salvarTipo", method = RequestMethod.POST)
	public String salvar(TipoRestaurante tipoRestaurante, RedirectAttributes attributes) {
		boolean alteracao = tipoRestaurante.getId() != null ? true : false;
		try {
			tipoRep.save(tipoRestaurante);
			attributes.addFlashAttribute("mensagemSucesso", "Tipo de restaurante salvo com sucesso");
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagemErro", "Erro ao realizar o cadastro: " + e.getMessage());
		}
		if (alteracao) {
			return "redirect:/listarTipo/1";
		} else {
			return "redirect:/formTipo";
		}

	}

	@RequestMapping("/listarTipo/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// caso queira ordenar por algum campo, acrescenta-se o Sort.by no 3º parâmetro
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoRestaurante> tipoPage = tipoRep.findAll(pageable);
		int totalPages = tipoPage.getTotalPages();
		model.addAttribute("tipos", tipoPage.getContent());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		model.addAttribute("pageNumbers", pageNumbers);
		return "tipoRestaurante/lista";
	}

	@RequestMapping("/alterarTipo")
	public String alterarTipo(Model model, Long idTipo) {
		TipoRestaurante tipo = tipoRep.findById(idTipo).get();
		model.addAttribute("tipo", tipo);
		return "forward:/formTipo";
	}

	@RequestMapping("/excluirTipo")
	public String excluirTipo(Long idTipo) {
		tipoRep.deleteById(idTipo);
		return "redirect:/listarTipo/1";
	}
}
