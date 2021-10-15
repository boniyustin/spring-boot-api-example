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
public class PurchaseOrder {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotNull
  private Long userId;
  private Long purchaseTimestamp;

  public PurchaseOrder(@NotNull Long userId, Long purchaseTimestamp) {
    this.userId = userId;
    this.purchaseTimestamp = purchaseTimestamp;
  }
}