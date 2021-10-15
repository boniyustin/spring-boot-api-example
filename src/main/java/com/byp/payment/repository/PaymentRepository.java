package com.byp.payment.repository;

import com.byp.order.entity.ShoppingCart;
import com.byp.payment.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
  List<Payment> findByUserId(Long userId);
}
