package br.com.ufc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.PurchaseOrder;
import br.com.ufc.service.PurchaseOrderService;
import br.com.ufc.service.ShoppingBagService;
import br.com.ufc.service.UserService;
import br.com.ufc.util.SessionUtils;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private ShoppingBagService shoppingBagService;

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public ModelAndView listMyPurchaseOrders() {
		List<PurchaseOrder> purchaseOrders = userService.findByEmail(SessionUtils.getCurrentUsername())
				.getPurchaseOrders();
		ModelAndView modelAndView = new ModelAndView("MyPurchaseOrders");
		modelAndView.addObject("purchaseOrders", purchaseOrders);

		return modelAndView;
	}

	@RequestMapping("/save")
	public ModelAndView createPurchaseOrder(HttpServletRequest request, HttpServletResponse response,
			@Validated PurchaseOrder purchaseOrder, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("ShoppingBag");
		modelAndView.addObject("dishes", shoppingBagService.getDishesInShoppingBag(request));

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		purchaseOrder.setDate(new Date());
		purchaseOrder.setUser(userService.findByEmail(SessionUtils.getCurrentUsername()));
		purchaseOrder.setValue(shoppingBagService.getTotalValueOfTheShoppingBag(request));

//		Clear shopping bar
		shoppingBagService.clearShoppingBag(response);

		purchaseOrderService.save(purchaseOrder);
		modelAndView.addObject("dishes", new ArrayList<>());
		modelAndView.addObject("message", "Pedido foi salvo com sucesso");

		return modelAndView;
	}

}
