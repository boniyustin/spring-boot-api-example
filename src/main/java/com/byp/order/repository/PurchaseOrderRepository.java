package com.byp.order.repository;

import com.byp.order.entity.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {
}
