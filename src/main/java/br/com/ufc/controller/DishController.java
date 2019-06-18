package br.com.ufc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.Dish;
import br.com.ufc.service.DishService;

@Controller
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;

	@RequestMapping("/list")
	public ModelAndView manageDishes() {
		List<Dish> dishes = dishService.list();
		ModelAndView modelAndView = new ModelAndView("DishList");
		modelAndView.addObject("dishes", dishes);

		return modelAndView;
	}

	@RequestMapping("/create")
	public ModelAndView createDish() {
		ModelAndView modelAndView = new ModelAndView("DishCreationForm");
		modelAndView.addObject("dish", new Dish());
		modelAndView.addObject("pageTitle", "Novo Prato");

		return modelAndView;
	}

	@RequestMapping("/update/{id}")
	public ModelAndView updateDish(@PathVariable Long id) {
		Dish dish = dishService.findById(id);
		ModelAndView modelAndView = new ModelAndView("DishCreationForm");
		modelAndView.addObject("dish", dish);
		modelAndView.addObject("pageTitle", "Editar Prato");

		return modelAndView;
	}

	@RequestMapping("/save")
	public ModelAndView saveDish(@Validated Dish dish, BindingResult bindingResult,
			@RequestParam(value = "dishImage") MultipartFile dishImage) {
		ModelAndView modelAndView = new ModelAndView("DishCreationForm");

		if (dish.getId() == null) {
			if (dishImage.isEmpty()) {
				bindingResult.addError(new ObjectError("dishImage", "Por favor, forneça uma imagem"));
			} else if (!(dishImage.getOriginalFilename().endsWith(".jpeg")
					|| dishImage.getOriginalFilename().endsWith(".jpg")
					|| dishImage.getOriginalFilename().endsWith(".png"))) {
				bindingResult.addError(new ObjectError("dishImage",
						"Por favor, forneça uma imagem em um formato válido (.jpeg, .jpg ou .png)"));
			}
		}

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		dishService.save(dish, dishImage);
		modelAndView.addObject("message", "Prato foi salvo com sucesso");

		return modelAndView;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteDish(@PathVariable Long id) {
		dishService.delete(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/dish/list");

		return modelAndView;
	}

}
