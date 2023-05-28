package com.example.ecommerce.controller;


import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.user.SignInWrapper;
import com.example.ecommerce.wrappers.user.SignInResponseWrapper;
import com.example.ecommerce.wrappers.user.SignupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.services.UserService;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseUtil signup(@RequestBody SignupWrapper signupWrapper) {
        return userService.signUp(signupWrapper);
    }

    @PostMapping("/signin")
    public SignInResponseWrapper signIn(@RequestBody SignInWrapper signInWrapper) {
        return userService.signIn(signInWrapper);
    }
}
