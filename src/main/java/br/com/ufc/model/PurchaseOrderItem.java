package br.com.ufc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseOrderItem {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
	private PurchaseOrder purchaseOrder;

	@ManyToOne
	@JoinColumn(name = "dish_id", referencedColumnName = "id")
	private Dish dish;

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

}
