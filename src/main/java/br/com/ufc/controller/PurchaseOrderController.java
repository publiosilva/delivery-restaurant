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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.PurchaseOrder;
import br.com.ufc.model.PurchaseOrderItem;
import br.com.ufc.service.PurchaseOrderItemService;
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
	private PurchaseOrderItemService purchaseOrderItemService;

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

	@RequestMapping("/details/{id}")
	public ModelAndView purchaseOrderDetails(@PathVariable Long id) {
		List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderService.findById(id).getPurchaseOrderItems();
		ModelAndView modelAndView = new ModelAndView("PurchaseOrderDetails");
		modelAndView.addObject("items", purchaseOrderItems);

		return modelAndView;
	}

	@RequestMapping("/save")
	public ModelAndView createPurchaseOrder(HttpServletRequest request, HttpServletResponse response,
			@Validated PurchaseOrder purchaseOrder, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("ShoppingBag");
		modelAndView.addObject("dishes", shoppingBagService.getItemsInShoppingBag(request));

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

//		Setting the purchase order of the purchase order items
		List<PurchaseOrderItem> purchaseOrderItems = shoppingBagService.getItemsInShoppingBag(request);
		for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
			purchaseOrderItem.setPurchaseOrder(purchaseOrder);
		}

//		Setting the purchase order data
		purchaseOrder.setDate(new Date());
		purchaseOrder.setUser(userService.findByEmail(SessionUtils.getCurrentUsername()));
		purchaseOrder.setAmount(shoppingBagService.getAmountOfTheShoppingBag(request));
		purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);

//		Saving the purchase order and his items
		purchaseOrderService.save(purchaseOrder);
		purchaseOrderItemService.saveAll(purchaseOrderItems);

//		Clear shopping bar
		shoppingBagService.clearShoppingBag(response);

		modelAndView.addObject("dishes", new ArrayList<>());
		modelAndView.addObject("amount", 0);
		modelAndView.addObject("message", "Pedido foi salvo com sucesso");

		return modelAndView;
	}

}
