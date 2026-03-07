package com.example.app.micro.paymentservice.application.service;

import org.springframework.stereotype.Service;

import com.example.app.micro.paymentservice.application.usecase.ConfirmPaymentUseCase;
import com.example.app.micro.paymentservice.application.usecase.CreatePaymentUseCase;
import com.example.app.micro.paymentservice.application.usecase.GetPaymentByOrderIdUseCase;
import com.example.app.micro.paymentservice.application.usecase.RefundPaymentUseCase;
import com.example.app.micro.paymentservice.domain.model.Payment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final ConfirmPaymentUseCase confirmPaymentUseCase;
    private final RefundPaymentUseCase refundPaymentUseCase;
    private final GetPaymentByOrderIdUseCase getPaymentByOrderIdUseCase;

    @Transactional
    public Payment createPayment(Payment payment) {
        return createPaymentUseCase.execute(payment);
    }

    @Transactional
    public Payment confirmPayment(Long paymentId) {
        return confirmPaymentUseCase.execute(paymentId);
    }

    @Transactional
    public Payment refundPayment(Long paymentId) {
        return refundPaymentUseCase.execute(paymentId);
    }

    @Transactional
    public Payment getByOrderId(Long orderId) {
        return getPaymentByOrderIdUseCase.execute(orderId);
    }
}
