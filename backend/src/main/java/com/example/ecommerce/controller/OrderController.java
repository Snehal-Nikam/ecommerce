package com.example.ecommerce.controller;

import com.example.ecommerce.services.AuthenticationService;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.wrappers.checkout.CheckoutItemWrapper;
import com.example.ecommerce.wrappers.checkout.StripeResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemWrapper> checkoutItemWrapperList)
    throws StripeException {
        Session session = orderService.createSession(checkoutItemWrapperList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}
