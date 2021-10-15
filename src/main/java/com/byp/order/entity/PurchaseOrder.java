package com.byp.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class PurchaseOrder {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotNull
  private Long userId;
  private int purchaseTime;

  public PurchaseOrder(@NotNull Long userId, int purchaseTime) {
    this.userId = userId;
    this.purchaseTime = purchaseTime;
  }
}