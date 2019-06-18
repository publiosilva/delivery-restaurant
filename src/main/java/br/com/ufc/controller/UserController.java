package br.com.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.User;
import br.com.ufc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/sign-in")
	public ModelAndView signInPage() {
		ModelAndView modelAndView = new ModelAndView("SignIn");

		return modelAndView;
	}

	@RequestMapping("/sign-up")
	public ModelAndView signUpPage() {
		ModelAndView modelAndView = new ModelAndView("SignUp");
		modelAndView.addObject("user", new User());

		return modelAndView;
	}

	@RequestMapping("/save")
	public ModelAndView saveRegister(@Validated User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("SignUp");

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		userService.save(user);
		modelAndView.addObject("message", "Usuário foi salvo com sucesso");

		return modelAndView;
	}

}
