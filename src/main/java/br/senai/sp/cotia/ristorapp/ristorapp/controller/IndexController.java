package br.senai.sp.cotia.ristorapp.ristorapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.sp.cotia.ristorapp.ristorapp.annotation.Publico;

@Controller
public class IndexController {
	
	@Publico
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
