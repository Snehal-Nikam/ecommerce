package com.example.ecommerce.wrappers.checkout;

public class StripeResponse {
    public String sessionId;

    public StripeResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
