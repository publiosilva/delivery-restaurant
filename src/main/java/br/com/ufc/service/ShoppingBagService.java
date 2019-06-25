package br.com.ufc.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.Dish;
import br.com.ufc.model.PurchaseOrderItem;
import br.com.ufc.util.CookieUtils;
import br.com.ufc.util.SessionUtils;

@Service
public class ShoppingBagService {

	@Autowired
	private DishService dishService;

	public void addItemAtShoppingBag(HttpServletRequest request, HttpServletResponse response, Long dishId) {
		String dishesInShoppingBag = CookieUtils.getValue(request, SessionUtils.getCurrentUsername());

		if (dishesInShoppingBag != null) {
			dishesInShoppingBag += String.valueOf(dishId) + "-";
		} else {
			dishesInShoppingBag = String.valueOf(dishId) + "-";
		}

		CookieUtils.setValue(response, SessionUtils.getCurrentUsername(), dishesInShoppingBag);
	}

	public List<PurchaseOrderItem> getItemsInShoppingBag(HttpServletRequest request) {
		String dishesInShoppingBagString = CookieUtils.getValue(request, SessionUtils.getCurrentUsername());
		List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();

		if (dishesInShoppingBagString != null && dishesInShoppingBagString.isEmpty() == false) {
			String[] dishesInShoppingBag = dishesInShoppingBagString.split("-");

			for (String dishId : dishesInShoppingBag) {
				Dish dish = dishService.findById(Long.parseLong(dishId));
				PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
				purchaseOrderItem.setDish(dish);
				purchaseOrderItems.add(purchaseOrderItem);
			}
		}

		return purchaseOrderItems;
	}

	public double getAmountOfTheShoppingBag(HttpServletRequest request) {
		List<PurchaseOrderItem> purchaseOrderItems = getItemsInShoppingBag(request);
		double amount = 0;

		for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
			amount += purchaseOrderItem.getDish().getPrice();
		}

		return amount;
	}

	public void removeItemFromShoppingBag(HttpServletRequest request, HttpServletResponse response, Long dishId) {
		String dishesInShoppingBag = CookieUtils.getValue(request, SessionUtils.getCurrentUsername());

		if (dishesInShoppingBag != null) {
			dishesInShoppingBag = dishesInShoppingBag.replace(dishId + "-", "");
		}

		CookieUtils.setValue(response, SessionUtils.getCurrentUsername(), dishesInShoppingBag);
	}

	public void clearShoppingBag(HttpServletResponse response) {
		CookieUtils.setValue(response, SessionUtils.getCurrentUsername(), null);
	}

}
