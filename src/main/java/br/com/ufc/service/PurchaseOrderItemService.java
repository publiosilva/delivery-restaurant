package br.com.ufc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.PurchaseOrderItem;
import br.com.ufc.repository.PurchaseOrderItemRepository;

@Service
public class PurchaseOrderItemService {

	@Autowired
	private PurchaseOrderItemRepository purchaseOrderItemRepository;

	public void saveAll(List<PurchaseOrderItem> purchaseOrderItems) {
		purchaseOrderItemRepository.saveAll(purchaseOrderItems);
	}

}
