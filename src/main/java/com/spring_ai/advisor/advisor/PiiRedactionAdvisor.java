package com.spring_ai.advisor.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;

import java.util.regex.Pattern;

public class PiiRedactionAdvisor implements CallAdvisor {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "\\b(?:\\+?1[-.]?)?\\(?([0-9]{3})\\)?[-.]?([0-9]{3})[-.]?([0-9]{4})\\b"
    );
    private static final Pattern CARD_PATTERN = Pattern.compile(
            "\\b(?:\\d[ -]*?){13,16}\\b"
    );
    private static final Pattern SSN_PATTERN = Pattern.compile(
            "\\b\\d{3}-?\\d{2}-?\\d{4}\\b"
    );

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        String originalMessage = request.prompt().getUserMessage().getText();
        //redacted original message
        String redactedMessage = redactPii(originalMessage);
        if (!originalMessage.equals(redactedMessage)) {
            System.out.println("[PII REDACTION] Sensitive data redacted from user message");
            System.out.println("Original: " + originalMessage);
            System.out.println("Redacted: " + redactedMessage);
        }
        ChatClientRequest modifiedRequest = request.mutate().prompt(request.prompt().augmentUserMessage(redactedMessage)).build();

        return chain.nextCall(modifiedRequest);
    }
    private String redactPii(String text) {
        // Handle null or empty input gracefully
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        // Apply each pattern sequentially
        // Order doesn't matter since patterns don't overlap
        String redacted = text;
        redacted = EMAIL_PATTERN.matcher(redacted).replaceAll("[EMAIL_REDACTED]");
        redacted = PHONE_PATTERN.matcher(redacted).replaceAll("[PHONE_REDACTED]");
        redacted = CARD_PATTERN.matcher(redacted).replaceAll("[CARD_REDACTED]");
        redacted = SSN_PATTERN.matcher(redacted).replaceAll("[SSN_REDACTED]");

        return redacted;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
