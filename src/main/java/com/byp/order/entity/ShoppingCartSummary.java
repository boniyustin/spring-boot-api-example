package com.byp.order.entity;

import com.byp.inventory.entity.Product;

public class ShoppingCartSummary extends Product {

  private Long shoppingCartId;
  private int quantity;

  public ShoppingCartSummary() {
    
  }

  public ShoppingCartSummary(Product product) {
    this.setId(product.getId());
    this.setName(product.getName());
    this.setDescription(product.getDescription());
    this.setPricePerItem(product.getPricePerItem());
    this.setCurrency(product.getCurrency());
    this.setTotalInventory(product.getTotalInventory());
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Long getShoppingCartId() {
    return shoppingCartId;
  }

  public void setShoppingCartId(Long shoppingCartId) {
    this.shoppingCartId = shoppingCartId;
  }
}