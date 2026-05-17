package com.spring_ai.advisor.model;

// Return Order Tool - Input/Output
public record ReturnRequest(
        String orderId,
        String reason
) {}
