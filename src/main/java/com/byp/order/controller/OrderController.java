package com.byp.order.controller;

import com.byp.inventory.entity.Product;
import com.byp.order.entity.ShoppingCart;
import com.byp.order.repository.OrderEntryRepository;
import com.byp.order.repository.PurchaseOrderRepository;
import com.byp.order.repository.ShoppingCartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

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

    @GetMapping(value = "/shoppingCarts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ShoppingCart> UpdateShoppingCart() {
        return shoppingCartRepository.findAll();
    }

   
}
