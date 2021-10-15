package com.byp.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PurchaseOrderItem {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotNull
  private Long purchaseOrderId;
  @NotNull
  private Long productId;
  private int quantity;
  private long pricePerItem;
  private String currency;

  public PurchaseOrderItem(Long purchaseOrderId, Long productId, int quantity,
                           long pricePerItem, String currency) {
    this.purchaseOrderId = purchaseOrderId;
    this.productId = productId;
    this.quantity = quantity;

    this.pricePerItem = pricePerItem;
    this.currency = currency;
  }
}