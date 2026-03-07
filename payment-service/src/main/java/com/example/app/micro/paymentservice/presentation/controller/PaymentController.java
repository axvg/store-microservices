package com.example.app.micro.paymentservice.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.micro.paymentservice.application.service.PaymentApplicationService;
import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.presentation.dto.CreatePaymentRequest;
import com.example.app.micro.paymentservice.presentation.dto.PaymentResponse;
import com.example.app.micro.paymentservice.presentation.mapper.PaymentDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentApplicationService paymentApplicationService;
    private final PaymentDtoMapper paymentDtoMapper;

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest request) {
        Payment created = paymentApplicationService.createPayment(paymentDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentDtoMapper.toResponse(created));
    }

    @PostMapping("/{paymentId}/confirm")
    public ResponseEntity<PaymentResponse> confirm(@PathVariable Long paymentId) {
        Payment confirmed = paymentApplicationService.confirmPayment(paymentId);
        return ResponseEntity.ok(paymentDtoMapper.toResponse(confirmed));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponse> refund(@PathVariable Long paymentId) {
        Payment refunded = paymentApplicationService.refundPayment(paymentId);
        return ResponseEntity.ok(paymentDtoMapper.toResponse(refunded));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentDtoMapper.toResponse(paymentApplicationService.getByOrderId(orderId)));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service running");
    }
}
