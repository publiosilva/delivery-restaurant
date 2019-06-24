package br.com.ufc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.Dish;
import br.com.ufc.service.DishService;

@Controller
public class HomePageController {

	@Autowired
	private DishService dishService;

	@RequestMapping("/")
	public ModelAndView homePage() {
		List<Dish> dishes = dishService.list();
		ModelAndView modelAndView = new ModelAndView("Home");
		modelAndView.addObject("dishes", dishes);

		return modelAndView;
	}

}
