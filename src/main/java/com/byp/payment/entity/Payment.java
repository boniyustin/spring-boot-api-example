package com.byp.payment.entity;

import com.byp.common.enums.PaymentStatus;
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
public class Payment {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @NotNull
  private Long purchaseOrderId;
  @NotNull
  private Long userId;

  private Long totalPayment;
  private PaymentStatus paymentStatus;
  private Long updatedTimestamp;

  public Payment(Long purchaseOrderId, Long userId, Long totalPayment, PaymentStatus paymentStatus, Long updatedTimestamp) {
    this.purchaseOrderId = purchaseOrderId;
    this.userId = userId;
    this.totalPayment = totalPayment;
    this.paymentStatus = paymentStatus;
    this.updatedTimestamp = updatedTimestamp;
  }
}