package com.hs.coursemanagerback.security.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EditRequest {

    private String company;

    @NotBlank
    @Email
    private String email;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
