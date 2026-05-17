package com.spring_ai.advisor.model;

public record RefundResponse(
        String orderId,
        String refundStatus,
        double refundAmount,
        String estimatedDate
) {}
