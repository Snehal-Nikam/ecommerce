package com.example.ecommerce.controller;


import com.example.ecommerce.exceptions.AuthenticationFailException;
//import com.example.ecommerce.model.Address;
//import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.User;
import com.example.ecommerce.services.AuthenticationService;
import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.profile.AddressWrapper;
import com.example.ecommerce.wrappers.profile.ProfileWrapper;
import com.example.ecommerce.wrappers.profile.UpdatePasswordWrapper;
import com.example.ecommerce.wrappers.profile.UserInfoWrapper;
import com.example.ecommerce.wrappers.user.SignInWrapper;
import com.example.ecommerce.wrappers.user.SignInResponseWrapper;
import com.example.ecommerce.wrappers.user.SignupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.services.UserService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseUtil signup(@RequestBody SignupWrapper signupWrapper) {
        return userService.signUp(signupWrapper);
    }

    @PostMapping("/signin")
    public SignInResponseWrapper signIn(@RequestBody SignInWrapper signInWrapper) {
        return userService.signIn(signInWrapper);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileWrapper> getProfile(@RequestParam("token") String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        ProfileWrapper profile = userService.getUserProfile(user);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping("/profile/updateUser")
    public ResponseEntity<ResponseUtil> updateUser(@RequestBody UserInfoWrapper userInfoWrapper,@RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        try {
            if(Objects.nonNull(user)){
                return new ResponseEntity<>(userService.updateUser(userInfoWrapper,user), HttpStatus.OK);
            }
            else return new ResponseEntity<>(new ResponseUtil(false, "Something went wrong"), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return new ResponseEntity<>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/updatePassword")
    public ResponseEntity<ResponseUtil> updateUserPassword(@RequestBody UpdatePasswordWrapper updatePasswordWrapper, @RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        try{
            if(Objects.isNull(user)){
                throw  new AuthenticationFailException("Something wrong with the session or user");
            }
            else if(Objects.nonNull(user)){
                return new ResponseEntity<>(userService.updatePassword(updatePasswordWrapper, user), HttpStatus.OK);
            }
            else return new ResponseEntity<>(new ResponseUtil(false, "Something went wrong"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return new ResponseEntity<>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/profile/updateAddress/{addressId}")
    public ResponseEntity<ResponseUtil> editAddress(@RequestBody AddressWrapper addressToUpdate, @PathVariable("addressId") Integer Id, @RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        try {
            if(Objects.nonNull(userService.getAddress(Id))){
                userService.updateAddress(Id , addressToUpdate, user);
                return new ResponseEntity<>(new ResponseUtil(true, "Address is updated successfully"), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new ResponseUtil(false, "Something went wrong"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return new ResponseEntity<>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/profile/createAddress")
    public ResponseEntity<ResponseUtil> createEntity(@Valid @RequestBody AddressWrapper address,@RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        try {
            userService.createAddress(address, user);
            return new ResponseEntity<>(new ResponseUtil(true, "Created a new address"), HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return new ResponseEntity<>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }



}
