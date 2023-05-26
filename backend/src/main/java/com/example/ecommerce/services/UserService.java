package com.example.ecommerce.services;

import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.utils.user.SignInUtil;
import com.example.ecommerce.utils.user.SignInResponseUtil;
import com.example.ecommerce.utils.user.SignupUtil;
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

    public ResponseUtil signUp(SignupUtil signupUtil) {
//        if (Object.notNull(userRepository.findByEmail(signupDto.getEmail()))) {
//            throw new CustomException("user already present");
//        }

        if (Objects.nonNull(userRepository.findByEmail(signupUtil.getEmail()))) {
            throw new CustomException("User already exist.");
        }

        String encryptedpassword = signupUtil.getPassword();
        try {
            encryptedpassword = hashPassword(signupUtil.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //logger.error("hashing password failed {}", e.getMessage());
        }
//        try {
//            encryptedpassword = hashPassword(signupDto.getPassword());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        User user = new User(signupUtil.getFirstName(), signupUtil.getLastname(), signupUtil.getEmail(), encryptedpassword);
        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
       // userRepository.findByEmail(signupDto.getEmail());
        ResponseUtil ResponseUtil = new ResponseUtil("success", "User created Successfully");
        return  ResponseUtil;
    }


    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseUtil signIn(SignInUtil signInUtil) {
        User user = userRepository.findByEmail(signInUtil.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User is not valid");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInUtil.getPassword()))) {
                throw new AuthenticationFailException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("Token is not present.");
        }

        return new SignInResponseUtil("Success", token.getToken());
    }
}
