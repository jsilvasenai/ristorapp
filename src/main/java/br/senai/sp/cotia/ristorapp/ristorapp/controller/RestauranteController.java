package br.senai.sp.cotia.ristorapp.ristorapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.cotia.ristorapp.ristorapp.model.Restaurante;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.RestauranteRepository;
import br.senai.sp.cotia.ristorapp.ristorapp.repository.TipoRepository;
import br.senai.sp.cotia.ristorapp.ristorapp.util.UploadUtil;

@Controller
public class RestauranteController {

	@Autowired
	private TipoRepository tipoRep;

	@Autowired
	private RestauranteRepository restRep;

	@RequestMapping("/formRestaurante")
	public String form(Model model) {
		model.addAttribute("tipos", tipoRep.findAll());
		return "restaurante/form";
	}

	@RequestMapping("/salvarRestaurante")
	public String salvar(Restaurante restaurante, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// caso o restaurante já exista, carrega para a String das fotos as fotos do
		// restaurante
		String fotos = restaurante.getId() == null ? "" : restaurante.getFotos();
		UploadUtil upload = new UploadUtil();
		for (MultipartFile file : fileFotos) {
			// o filefotos sempre vem com 1 arquivo mesmo quando não selecionado
			if (file.getOriginalFilename().isEmpty()) {
				continue;
			}
			try {
				fotos += upload.uploadFile(file) + ";";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		restaurante.setFotos(fotos);
		restRep.save(restaurante);
		return "redirect:formRestaurante";
	}

	@RequestMapping("/listarRestaurante/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// caso queira ordenar por algum campo, acrescenta-se o Sort.by no 3º parâmetro
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Restaurante> restaPage = restRep.findAll(pageable);
		int totalPages = restaPage.getTotalPages();
		model.addAttribute("rests", restaPage.getContent());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		model.addAttribute("pageNumbers", pageNumbers);
		return "restaurante/lista";
	}

	@RequestMapping("/alterarRest")
	public String alterarTipo(Model model, Long idRest) {
		Restaurante restaurante = restRep.findById(idRest).get();
		model.addAttribute("restaurante", restaurante);
		model.addAttribute("fotosRest", restaurante.getFotos().split(";"));
		return "forward:/formRestaurante";
	}
	
	
	@RequestMapping("/excluirFotoRestaurante")
	public void excluirFoto(String fotoUrl, HttpServletResponse response) {
		System.out.println(fotoUrl);
	}

}
