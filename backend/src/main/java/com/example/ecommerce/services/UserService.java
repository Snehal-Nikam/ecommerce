package com.example.ecommerce.services;

import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.user.SignInWrapper;
import com.example.ecommerce.wrappers.user.SignInResponseWrapper;
import com.example.ecommerce.wrappers.user.SignupWrapper;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.repos.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.User;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional

    public ResponseUtil signUp(SignupWrapper signupWrapper) {
//        if (Object.notNull(userRepository.findByEmail(signupDto.getEmail()))) {
//            throw new CustomException("user already present");
//        }

        if (Objects.nonNull(userRepository.findByEmail(signupWrapper.getEmail()))) {
            throw new CustomException("User already exist.");
        }

        String encryptedpassword = signupWrapper.getPassword();
        try {
            encryptedpassword = hashPassword(signupWrapper.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //logger.error("hashing password failed {}", e.getMessage());
        }
//        try {
//            encryptedpassword = hashPassword(signupDto.getPassword());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        User user = new User(signupWrapper.getFirstName(), signupWrapper.getLastname(), signupWrapper.getEmail(), encryptedpassword);
        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
       // userRepository.findByEmail(signupDto.getEmail());
        ResponseUtil ResponseUtil = new ResponseUtil(true, "User created Successfully");
        return  ResponseUtil;
    }


    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseWrapper signIn(SignInWrapper signInWrapper) {
        User user = userRepository.findByEmail(signInWrapper.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User is not valid");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInWrapper.getPassword()))) {
                throw new AuthenticationFailException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("Token is not present.");
        }

        return new SignInResponseWrapper("Success", token.getToken());
    }
}
