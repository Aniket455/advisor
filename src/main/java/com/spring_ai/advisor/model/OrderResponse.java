package com.spring_ai.advisor.model;

public record OrderResponse(
        String orderId,
        String status,
        String estimatedDelivery,
        String trackingNumber,
        String customerName,
        double totalAmount
) {}
