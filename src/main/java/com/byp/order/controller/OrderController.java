package com.byp.order.controller;

import com.byp.common.enums.PaymentStatus;
import com.byp.inventory.controller.InventoryController;
import com.byp.inventory.entity.Product;
import com.byp.order.entity.PurchaseOrder;
import com.byp.order.entity.PurchaseOrderItem;
import com.byp.order.entity.ShoppingCart;
import com.byp.order.entity.ShoppingCartSummary;
import com.byp.order.repository.OrderEntryRepository;
import com.byp.order.repository.PurchaseOrderItemRepository;
import com.byp.order.repository.PurchaseOrderRepository;
import com.byp.order.repository.ShoppingCartRepository;
import com.byp.payment.controller.PaymentController;
import com.byp.payment.entity.Payment;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
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
    private final PaymentController paymentController;
    private final OrderEntryRepository orderEntryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderController(InventoryController inventoryController,
                           PaymentController paymentController,
                           OrderEntryRepository orderEntryRepository,
                           PurchaseOrderRepository purchaseOrderRepository,
                           PurchaseOrderItemRepository purchaseOrderItemRepository,
                           ShoppingCartRepository shoppingCartRepository) {
        this.inventoryController = inventoryController;
        this.paymentController = paymentController;
        this.orderEntryRepository = orderEntryRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
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
        ShoppingCart existingCart = shoppingCartRepository.findByUserIdAndProductId(shoppingCart.getUserId(), shoppingCart.getProductId());
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + shoppingCart.getQuantity());
            return shoppingCartRepository.save(existingCart);
        } else {
            return shoppingCartRepository.save(shoppingCart);
        }
    }

    @PostMapping(value = "/shopping-cart/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void removeFromCart(@Valid @RequestBody ShoppingCart shoppingCart) {
        shoppingCartRepository.deleteByProductId(shoppingCart.getProductId());
    }

    @PostMapping(value = "/order/purchase", produces = MediaType.APPLICATION_JSON_VALUE)
    public void purchaseOrder(@Valid @RequestBody List<ShoppingCart> shoppingCarts) {
        if (CollectionUtils.isEmpty(shoppingCarts)){
            return;
        }
        
        // set purchase order
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(shoppingCarts.get(0).getUserId());
        purchaseOrder.setPurchaseTimestamp(System.currentTimeMillis());
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        // set purchase order items
        Iterable<Product> products = inventoryController.getProductsByIds(getProductIds(shoppingCarts));
        Map<Long, Product> productMap = toProductMap(products);

        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
        Long totalPayment = 0L;
        for (ShoppingCart shoppingCart: shoppingCarts) {
            Product product = productMap.get(shoppingCart.getProductId());
            PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
            purchaseOrderItem.setPurchaseOrderId(purchaseOrder.getId());
            purchaseOrderItem.setProductId(shoppingCart.getProductId());
            purchaseOrderItem.setQuantity(shoppingCart.getQuantity());
            purchaseOrderItem.setPricePerItem(product.getPricePerItem());
            purchaseOrderItem.setCurrency(product.getCurrency());
            totalPayment += (purchaseOrderItem.getQuantity() * purchaseOrderItem.getPricePerItem());
            purchaseOrderItems.add(purchaseOrderItem);
        }
        purchaseOrderItemRepository.saveAll(purchaseOrderItems);

        // set payment
        Payment payment = new Payment();
        payment.setPurchaseOrderId(purchaseOrder.getId());
        payment.setUserId(purchaseOrder.getUserId());
        payment.setTotalPayment(totalPayment);
        payment.setUpdatedTimestamp(System.currentTimeMillis());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        paymentController.insertPayment(payment);
    }

    private Map<Long, Product> toProductMap(Iterable<Product> products) {
        Map<Long, Product> productMap = new HashMap<>();
        for (Product product: products) {
            productMap.put(product.getId(), product);
            //}
        }
        return productMap;
    }

    private Map<Long, ShoppingCart> getProductShoppingCartMap(Iterable<ShoppingCart> shoppingCarts) {
        Map<Long, ShoppingCart> productShoppingCartMap = new HashMap<>();
        for (ShoppingCart shoppingCart: shoppingCarts) {
            //if (productShoppingCartMap.containsKey(shoppingCart.getProductId())) {
            //    ShoppingCart existingShoppingCart = productShoppingCartMap.get(shoppingCart.getProductId());
            //    existingShoppingCart.setQuantity(existingShoppingCart.getQuantity() + shoppingCart.getQuantity());
            //} else {
            productShoppingCartMap.put(shoppingCart.getProductId(), shoppingCart);
            //}
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
