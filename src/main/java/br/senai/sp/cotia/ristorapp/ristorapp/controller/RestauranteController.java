package br.senai.sp.cotia.ristorapp.ristorapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.sp.cotia.ristorapp.ristorapp.repository.TipoRepository;

@Controller
public class RestauranteController {
	
	@Autowired
	private TipoRepository tipoRep;
	
	@RequestMapping("/formRestaurante")
	public String form(Model model) {
		model.addAttribute("tipos", tipoRep.findAll());
		return "restaurante/form";
	}
}
