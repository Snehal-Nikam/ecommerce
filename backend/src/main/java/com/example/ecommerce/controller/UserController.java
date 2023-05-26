package com.example.ecommerce.controller;


import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.utils.user.SignInUtil;
import com.example.ecommerce.utils.user.SignInResponseUtil;
import com.example.ecommerce.utils.user.SignupUtil;
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
    public ResponseUtil signup(@RequestBody SignupUtil signupUtil) {
        return userService.signUp(signupUtil);
    }

    @PostMapping("/signin")
    public SignInResponseUtil signIn(@RequestBody SignInUtil signInUtil) {
        return userService.signIn(signInUtil);
    }
}
