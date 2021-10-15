package com.byp.order.controller;

import com.byp.inventory.controller.InventoryController;
import com.byp.inventory.entity.Product;
import com.byp.order.entity.ShoppingCart;
import com.byp.order.entity.ShoppingCartSummary;
import com.byp.order.repository.OrderEntryRepository;
import com.byp.order.repository.PurchaseOrderRepository;
import com.byp.order.repository.ShoppingCartRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OrderController {
    private final InventoryController inventoryController;
    private final OrderEntryRepository orderEntryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderController(InventoryController inventoryController,
                           OrderEntryRepository orderEntryRepository,
                           PurchaseOrderRepository purchaseOrderRepository,
                           ShoppingCartRepository shoppingCartRepository) {
        this.inventoryController = inventoryController;
        this.orderEntryRepository = orderEntryRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @GetMapping(value = "/shopping-carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShoppingCartSummary> getShoppingCartSummaries(@RequestBody Long userId) {
        Iterable<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(userId);
        Map<Long, ShoppingCart> productAmount = getProductShoppingCartMap(shoppingCarts);

        Iterable<Product> products = inventoryController.getProductsByIds(getProductIds(shoppingCarts));
        List<ShoppingCartSummary> summaries = new ArrayList<>();
        for(Product product: products) {
            ShoppingCartSummary summary = new ShoppingCartSummary(product);
            ShoppingCart shoppingCart = productAmount.get(product.getId());
            summary.setQuantity(shoppingCart.getQuantity());
            summary.setShoppingCartId(shoppingCart.getId());
            summaries.add(summary);
        }
        return summaries;
    }



    @PostMapping(value = "/shopping-cart/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCart addToCart(@Valid @RequestBody ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @PostMapping(value = "/shopping-cart/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void removeFromCart(@Valid @RequestBody ShoppingCart shoppingCart) {
        shoppingCartRepository.deleteByProductId(shoppingCart.getProductId());
    }

    private Map<Long, ShoppingCart> getProductShoppingCartMap(Iterable<ShoppingCart> shoppingCarts) {
        Map<Long, ShoppingCart> productShoppingCartMap = new HashMap<>();
        for (ShoppingCart shoppingCart: shoppingCarts) {
            if (productShoppingCartMap.containsKey(shoppingCart.getProductId())) {
                ShoppingCart existingShoppingCart = productShoppingCartMap.get(shoppingCart.getProductId());
                existingShoppingCart.setQuantity(existingShoppingCart.getQuantity() + shoppingCart.getQuantity());
            } else {
                productShoppingCartMap.put(shoppingCart.getProductId(), shoppingCart);
            }
        }
        return productShoppingCartMap;
    }

    private List<Long> getProductIds(Iterable<ShoppingCart> shoppingCarts) {
        List<Long> productIds = new ArrayList<>();
        for(ShoppingCart shoppingCart: shoppingCarts) {
            productIds.add(shoppingCart.getProductId());
        }
        
        return productIds;
    }
    

   
}
