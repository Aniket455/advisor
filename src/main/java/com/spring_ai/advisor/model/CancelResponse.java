package com.spring_ai.advisor.model;
public record CancelResponse(
        boolean success,
        String message,
        String orderId,
        double refundAmount
) {}
