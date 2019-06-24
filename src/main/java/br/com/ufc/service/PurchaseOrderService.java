package br.com.ufc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.PurchaseOrder;
import br.com.ufc.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	public void save(PurchaseOrder purchaseOrder) {
		purchaseOrderRepository.save(purchaseOrder);
	}

}
