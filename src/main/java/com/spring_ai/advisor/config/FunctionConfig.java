package com.spring_ai.advisor.config;


import com.spring_ai.advisor.model.CancelResponse;
import com.spring_ai.advisor.model.OrderResponse;
import com.spring_ai.advisor.model.RefundResponse;
import com.spring_ai.advisor.model.ReturnResponse;
import com.spring_ai.advisor.service.OrderService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunctionConfig {

    @Autowired
    private OrderService orderService;

    /* @param orderId The order ID to check
     * @return OrderResponse with tracking info and delivery estimates
     */
    @Tool(description = "get order status, tracking info and delivery estimates")
    public OrderResponse getOrderStatus(
            @ToolParam(description = "order id to check") String orderId){
        System.out.println("🔧 AI used tool: getOrderStatus(" + orderId + ")");
        return orderService.getOrderStatus(orderId);
    }

    /* @param orderId The order ID to cancel
     * @param reason Why the customer wants to cancel (for records)
     * @return CancelResponse with success status and refund info
     */
    @Tool(description = "Cancel an order that hasn't shipped yet. Returns refund info if successful.")
    public CancelResponse cancelOrder(
            @ToolParam(description = "The order ID to cancel")
            String orderId,
            @ToolParam(description = "Reason for cancellation")
            String reason
    ) {
        System.out.println("🔧 AI used tool: cancelOrder(" + orderId + ", reason: " + reason + ")");
        return orderService.cancelOrder(orderId, reason);
    }

    /* @param orderId The order ID to return
     * @param reason Why the customer is returning (required for processing)
     * @return ReturnResponse with return label and instructions
     */
    @Tool(description = "Start return process for a delivered order. Customer must provide reason for return.")
    public ReturnResponse initiateReturn(
            @ToolParam(description = "The order ID to return")
            String orderId,
            @ToolParam(description = "Reason for return")
            String reason
    ) {
        System.out.println("🔧 AI used tool: initiateReturn(" + orderId + ", reason: " + reason + ")");
        return orderService.initiateReturn(orderId, reason);
    }

    /* @param orderId The order ID to check refund status for
     * @return RefundResponse with refund status, amount, and date
     */
    @Tool(description = "Check the status of a refund for a cancelled order")
    public RefundResponse checkRefund(
            @ToolParam(description = "The order ID to check refund status for")
            String orderId
    ) {
        System.out.println("🔧 AI used tool: checkRefund(" + orderId + ")");
        return orderService.checkRefund(orderId);
    }
}
