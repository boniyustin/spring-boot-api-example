package com.byp.order.repository;

import com.byp.order.entity.PurchaseOrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepository extends CrudRepository<PurchaseOrderItem, Long> {
}
