package com.example.app.micro.paymentservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.paymentservice.domain.exception.InvalidPaymentDataException;
import com.example.app.micro.paymentservice.domain.exception.PaymentNotFoundException;
import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.domain.model.PaymentStatus;
import com.example.app.micro.paymentservice.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConfirmPaymentUseCase {
    private final PaymentRepository paymentRepository;

    public Payment execute(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id " + paymentId + " not found"));

        if (PaymentStatus.REFUNDED.equals(payment.getStatus())) {
            throw new InvalidPaymentDataException("Refunded payment cannot be confirmed");
        }

        payment.setStatus(PaymentStatus.CONFIRMED);
        return paymentRepository.save(payment);
    }
}
