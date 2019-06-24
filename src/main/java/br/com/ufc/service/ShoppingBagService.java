package br.com.ufc.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.Dish;
import br.com.ufc.util.CookieUtils;
import br.com.ufc.util.SessionUtils;

@Service
public class ShoppingBagService {

	@Autowired
	private DishService dishService;

	public void addDishAtShoppingBag(HttpServletRequest request, HttpServletResponse response, Long id) {
		String dishesInShoppingBag = CookieUtils.getValue(request, SessionUtils.getCurrentUsername());

		if (dishesInShoppingBag != null) {
			dishesInShoppingBag += String.valueOf(id) + "-";
		} else {
			dishesInShoppingBag = String.valueOf(id) + "-";
		}

		CookieUtils.setValue(response, SessionUtils.getCurrentUsername(), dishesInShoppingBag);
	}

	public List<Dish> getDishesInShoppingBag(HttpServletRequest request) {
		String dishesInShoppingBagString = CookieUtils.getValue(request, SessionUtils.getCurrentUsername());
		List<Dish> dishes = new ArrayList<Dish>();

		if (dishesInShoppingBagString != null && dishesInShoppingBagString.isEmpty() == false) {
			String[] dishesInShoppingBag = dishesInShoppingBagString.split("-");

			for (String dishId : dishesInShoppingBag) {
				Dish dish = dishService.findById(Long.parseLong(dishId));
				dishes.add(dish);
			}
		}

		return dishes;
	}

	public double getTotalValueOfTheShoppingBag(HttpServletRequest request) {
		List<Dish> dishes = getDishesInShoppingBag(request);
		double totalValue = 0;

		for (Dish dish : dishes) {
			totalValue += dish.getPrice();
		}

		return totalValue;
	}

	public void removeDishFromShoppingBag(HttpServletRequest request, HttpServletResponse response, Long id) {
		String dishesInShoppingBag = CookieUtils.getValue(request, SessionUtils.getCurrentUsername());

		if (dishesInShoppingBag != null) {
			dishesInShoppingBag = dishesInShoppingBag.replace(id + "-", "");
		}

		CookieUtils.setValue(response, SessionUtils.getCurrentUsername(), dishesInShoppingBag);
	}

	public void clearShoppingBag(HttpServletResponse response) {
		CookieUtils.setValue(response, SessionUtils.getCurrentUsername(), null);
	}

}
