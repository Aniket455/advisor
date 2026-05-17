package com.spring_ai.advisor.model;
// Cancel Order Tool - Input/Output
public record CancelRequest(
        String orderId,
        String reason
) {}
