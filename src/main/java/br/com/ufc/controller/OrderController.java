package br.com.ufc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

	@RequestMapping("/my-orders")
	public ModelAndView myDishes() {
		ModelAndView modelAndView = new ModelAndView("MyOrders");

		return modelAndView;
	}

}
