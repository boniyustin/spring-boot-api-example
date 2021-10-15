package com.byp.order.repository;

import com.byp.order.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
  List<ShoppingCart> findByUserId(Long userId);
  ShoppingCart findByUserIdAndProductId(Long userId, Long productId);
  void deleteByProductId(Long productId);
}
