package com.spring_ai.advisor.model;

public record ReturnResponse(
        boolean success,
        String message,
        String returnId,
        String returnLabel
) {}
