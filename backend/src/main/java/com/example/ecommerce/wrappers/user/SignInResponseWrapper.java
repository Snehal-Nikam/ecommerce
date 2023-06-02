package com.example.ecommerce.wrappers.user;

public class SignInResponseWrapper {
    private String status;
    private String token;

    private boolean isAdmin;


    public SignInResponseWrapper(String status, String token, boolean isAdmin) {
        this.status = status;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
