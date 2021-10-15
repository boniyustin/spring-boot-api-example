package com.byp.payment.controller;

import com.byp.common.enums.PaymentStatus;
import com.byp.payment.entity.Payment;
import com.byp.payment.repository.PaymentRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PaymentController {
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @PostMapping(value = "/payment/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Payment insertPayment(@Valid @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }
    
    @PostMapping(value = "/payment/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void updatePaymentStatus(@Valid @RequestBody Payment payment) {
        Optional<Payment> existingPayment = paymentRepository.findById(payment.getId());
        if (!existingPayment.isPresent()) {
            throw new NullPointerException("Payment is not found");
        }

        boolean isAllowToUpdateStatus = checkStatus(existingPayment.get().getPaymentStatus(), payment.getPaymentStatus());
        if (!isAllowToUpdateStatus) {
            throw new IllegalStateException("Not allowed to update status");
        }
        
        existingPayment.get().setPaymentStatus(payment.getPaymentStatus());
        paymentRepository.save(existingPayment.get());
    }

    private boolean checkStatus(PaymentStatus previousStatus, PaymentStatus nextStatus) {
        if (previousStatus == PaymentStatus.PAID) {
            return false;
        }

        if (previousStatus == PaymentStatus.CONFIRM_BY_USER) {
            return nextStatus == PaymentStatus.PAID;
        }

        return true;
    }


}
