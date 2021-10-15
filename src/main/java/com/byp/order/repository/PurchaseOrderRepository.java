package com.byp.order.repository;

import com.byp.order.entity.OrderEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends CrudRepository<OrderEntry, Long> {
}
