package br.com.ufc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.PurchaseOrder;
import br.com.ufc.model.PurchaseOrderItem;
import br.com.ufc.repository.PurchaseOrderRepository;
import br.com.ufc.util.SessionUtils;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private MailSenderService mailSenderService;

	public void save(PurchaseOrder purchaseOrder) {
		purchaseOrderRepository.save(purchaseOrder);

//		Send mail
		sendMail(purchaseOrder.getPurchaseOrderItems());
	}

	public PurchaseOrder findById(Long id) {
		return purchaseOrderRepository.getOne(id);
	}

	private void sendMail(List<PurchaseOrderItem> purchaseOrderItems) {
		double amount = 0;
		String text = "";

		for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
			amount += purchaseOrderItem.getDish().getPrice();
			text += "+ " + purchaseOrderItem.getDish().getName() + " | R$" + purchaseOrderItem.getDish().getPrice()
					+ "\n";
		}

		text += "# TOTAL: R$ " + amount + " #";
		mailSenderService.sendMail(SessionUtils.getCurrentUsername(), "Novo pedido efetuado no The Food", text);
	}

}
