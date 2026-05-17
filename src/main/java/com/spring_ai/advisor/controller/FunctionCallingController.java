package com.spring_ai.advisor.controller;


import com.spring_ai.advisor.service.FunctionCallingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/support")
public class FunctionCallingController {

    @Autowired
    private FunctionCallingService functionCallingService;


    @PostMapping("/chat/basic")
    public Map<String, Object> reviewCode(
            @RequestBody Map<String, String> request,
            @RequestParam(defaultValue = "openai") String provider,
            @RequestParam(defaultValue = "openai/gpt-oss-20b") String model) {
    String userMessage = request.get("message");
    if (userMessage == null || userMessage.trim().isEmpty()){
        return Map.of(
                "success",false,
                "error","message cannot be empty"
        );
    }

    try {
        String response = functionCallingService.chatWithOrderTracking(userMessage,provider,model);
        return Map.of(
                "success",true,
                "response",response
        );
    } catch (Exception e) {
        return Map.of(
                "success",false,
                "error","Failed : "+e.getMessage()
        );
    }
    }


    @PostMapping("/chat/full")
    public Map<String, Object> fullSupportChat(
            @RequestBody Map<String, String> request,
            @RequestParam(defaultValue = "openai") String provider,
            @RequestParam(defaultValue = "openai/gpt-oss-20b") String model
    ) {
        String userMessage = request.get("message");

        // Input validation
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return Map.of(
                    "success", false,
                    "error", "Message cannot be empty"
            );
        }

        try {
            // Call service with FULL tool set
            // AI has access to ALL functions and intelligently chooses
            // which one(s) to call based on user's message
            String response = functionCallingService.chatWithFullSupport(
                    userMessage, provider, model);

            return Map.of(
                    "success", true,
                    "response", response
            );

        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "error", "Failed: " + e.getMessage()
            );
        }
    }
}
