package br.senac.tads.dsw.exemplosspring;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FormularioController {
	
	@Autowired
	private DadosPessoaisService service;
	
	@GetMapping("/lista")
	public ModelAndView listar() {
		return new ModelAndView("lista").addObject("dados", service.findAll());
	}

	@GetMapping("/formulario")
	public ModelAndView abrirForm() {
		//return new ModelAndView("formulario").addObject("dados", new DadosPessoais())
		DadosPessoais dados = new DadosPessoais();
		dados.setId(2);
		dados.setNome("kaio hvs");
		dados.setDescricao("kaio voce tem que crescer");
		dados.setDtNascimento(LocalDate.of(1997, 3, 6));
		dados.setEmail("kaio@trste.com");
		dados.setNumeroSorte(7);
		dados.setAltura(new BigDecimal("1.80"));
		dados.setPeso(new BigDecimal("80.0"));
		dados.setSenha("1234");
		dados.setRepetirSenha("1234");
		dados.setSexo(1);
		dados.setInteresses(Arrays.asList("Esportes", "Tecnologia"));	
		return new ModelAndView("formulario").addObject("dados", dados);
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute DadosPessoais dadosRecebidos) {
		// SALVAR NO BD
		service.save(dadosRecebidos);
		return new ModelAndView("resultado").addObject("dados", dadosRecebidos);
	}
	
	@PostMapping("/salvar-prg")
	public ModelAndView salvarPrg(@ModelAttribute DadosPessoais dadosRecebidos, RedirectAttributes redirAttr) {
		service.save(dadosRecebidos);
		redirAttr.addFlashAttribute("dados", dadosRecebidos);
		return new ModelAndView("redirect:/resultado-prg");
	}
	@PostMapping("/salvar-validacao-prg")
	public ModelAndView salvarValidacaoPrg(
			@ModelAttribute("dados") @Valid DadosPessoais dadosRecebidos,
			BindingResult bindingResult, RedirectAttributes redirAttr) {
				
		if(bindingResult.hasErrors()) {
			return new ModelAndView("formulario");
		}
		service.save(dadosRecebidos);
				redirAttr.addFlashAttribute("dados", dadosRecebidos);
	return new ModelAndView("redirect:/resultado-prg");
	}
	
	
	@GetMapping("/resultado-prg")
	public ModelAndView resultadoPrg() {
		return new ModelAndView("resultado");
	}
}
