package com.byp.order.controller;

import com.byp.order.repository.OrderEntryRepository;
import com.byp.order.repository.PurchaseOrderRepository;
import com.byp.order.repository.ShoppingCartRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderEntryRepository orderEntryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderController(OrderEntryRepository orderEntryRepository,
                           PurchaseOrderRepository purchaseOrderRepository,
                           ShoppingCartRepository shoppingCartRepository) {
        this.orderEntryRepository = orderEntryRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }
}
