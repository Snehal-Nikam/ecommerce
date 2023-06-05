package com.example.ecommerce.controller;

import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.OrderNotFoundException;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repos.OrderRepository;
import com.example.ecommerce.services.AuthenticationService;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.checkout.CheckoutItemWrapper;
import com.example.ecommerce.wrappers.checkout.StripeResponse;
import com.example.ecommerce.wrappers.order.OrderUpdateWrapper;
import com.example.ecommerce.wrappers.order.OrderWrapper;
import com.example.ecommerce.wrappers.profile.AddressWrapper;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemWrapper> checkoutItemWrapperList)
    throws StripeException {
        Session session = orderService.createSession(checkoutItemWrapperList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseUtil> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ResponseUtil(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Object>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        List<Object> orderDtoList = new ArrayList<>();
        // get orders
        if(user.isAdmin()) {
            //orderDtoList= Collections.singletonList((Object) orderService.listOrders());
            List<OrderWrapper> lstWrapper = new ArrayList<>();
            List<Order> orders = orderService.listOrders();
            for(Order o: orders){
                lstWrapper.add(new OrderWrapper(o.getId(), o.getUser().getId(),o.getUser().getFirstName()+" "+o.getUser().getLastName(), o.getUser().getEmail(), null, null , o.getTotalPrice(),o.getStatus()));
            }
            orderDtoList= Collections.singletonList((Object) lstWrapper);

        }
        else
            orderDtoList = Collections.singletonList(orderService.listOrders(user));

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/update/{orderId}")
    public ResponseEntity<ResponseUtil> updateOder(@RequestBody OrderUpdateWrapper orderWrapper, @PathVariable("orderId") Integer Id, @RequestParam("token") String token){

        try {
            authenticationService.authenticate(token);
            User user = authenticationService.getUser(token);
            Order order = orderService.getOrder(Id);
            if(Objects.nonNull(order)){
                if(!user.isAdmin() && orderWrapper.getStatus().equalsIgnoreCase("Cancel"))
                    order.setStatus(orderWrapper.getStatus());
                else if(user.isAdmin())
                    order.setStatus(orderWrapper.getStatus());
                else
                    return new ResponseEntity<>(new ResponseUtil(true, "You don't have necessary privilege to update the status"), HttpStatus.FORBIDDEN);

                orderRepository.save(order);

                return new ResponseEntity<>(new ResponseUtil(true, "Order is updated successfully"), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new ResponseUtil(false, "Order is not in the system"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return new ResponseEntity<>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
