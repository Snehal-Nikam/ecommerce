package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.AddressNotFoundException;
import com.example.ecommerce.exceptions.ProductNotExistsException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repos.AddressRepository;
import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.product.ProductWrapper;
import com.example.ecommerce.wrappers.profile.AddressWrapper;
import com.example.ecommerce.wrappers.profile.ProfileWrapper;
import com.example.ecommerce.wrappers.user.SignInWrapper;
import com.example.ecommerce.wrappers.user.SignInResponseWrapper;
import com.example.ecommerce.wrappers.user.SignupWrapper;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.repos.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AddressRepository addressRepository;

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

    public ProfileWrapper getUserProfile(User user){
        List<Address> lstAddress = addressRepository.findAllByUser(user);
//        final List<AddressWrapper> address = new ArrayList<>();
//        for (Address a:lstAddress) {
//            address.add(createAddressWrapper(a));
//        }
        ProfileWrapper profileData= new ProfileWrapper();
        profileData.setFirstName(user.getFirstName());
        profileData.setLastName(user.getLastName());
        profileData.setEmail(user.getEmail());
        profileData.setAddresses(lstAddress);
        //To do : Order
        return profileData;
    }

//    public AddressWrapper createAddressWrapper(Address address){
//        AddressWrapper addressWrapper = new AddressWrapper();
//        addressWrapper.setAddressLine1(address.getAddressLine1());
//        addressWrapper.setAddressLine2(address.getAddressLine2());
//        addressWrapper.setCity(address.getCity());
//        addressWrapper.setPrimary(address.isPrimary());
//        addressWrapper.setPhone(address.getPhone());
//        addressWrapper.setZipCode(address.getZipCode());
//        addressWrapper.setState(addressWrapper.getState());
//        addressWrapper.setId(address.getId());
//        return addressWrapper;
//    }
    public Address getAddressFromWrapper(AddressWrapper addressWrapper, User user){
        Address address = new Address();
        address.setAddressLine1(addressWrapper.getAddressLine1());
        address.setAddressLine2(addressWrapper.getAddressLine2());
        address.setCity(addressWrapper.getCity());
        address.setPrimary(addressWrapper.isPrimary());
        address.setPhone(addressWrapper.getPhone());
        address.setZipCode(addressWrapper.getZipCode());
        address.setState(addressWrapper.getState());
        address.setUser(user);
        return address;
    }

    public void updateAddress(Integer addressId, AddressWrapper addressToUpdate, User user) {
        Address address = getAddressFromWrapper(addressToUpdate, user);
        address.setId(addressId);
        addressRepository.save(address);
    }

    public Address getAddress(Integer addressId) throws AddressNotFoundException {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new AddressNotFoundException("Address id is invalid: " + addressId);
        }
        return optionalAddress.get();
    }

    public void createAddress(AddressWrapper addressWrapper, User user){
        Address address = getAddressFromWrapper(addressWrapper, user);
        addressRepository.save(address);
    }

}
