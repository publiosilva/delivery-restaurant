package br.com.ufc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.PurchaseOrder;
import br.com.ufc.service.ShoppingBagService;

@Controller
@RequestMapping("/shopping-bag")
public class ShoppingBagController {

	@Autowired
	private ShoppingBagService shoppingBagService;

	@RequestMapping("")
	public ModelAndView shoppingBag(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("ShoppingBag");
		modelAndView.addObject("dishes", shoppingBagService.getDishesInShoppingBag(request));
		modelAndView.addObject("purchaseOrder", new PurchaseOrder());

		return modelAndView;
	}

	@RequestMapping("/add-dish/{id}")
	public ModelAndView addDishAtShoppingBag(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/shopping-bag");
		shoppingBagService.addDishAtShoppingBag(request, response, id);

		return modelAndView;
	}

	@RequestMapping("/remove-dish/{id}")
	public ModelAndView removeDishFromShoppingBag(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/shopping-bag");
		shoppingBagService.removeDishFromShoppingBag(request, response, id);

		return modelAndView;
	}

}
