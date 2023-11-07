package com.ecommerce.library.dto;
import com.ecommerce.library.model.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    @Size(min = 1, max = 20, message = "First name should have 1-20 characters")
    private String firstName;
    @Size(min = 1, max = 100, message = "Last name should have 1-100 characters")
    private String lastName;
    private String username;
    @Size(min = 5,max = 15, message = "Invalid password! (5-15 characters)")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
            message = "The password must contain at least one digit, one uppercase letter, and one special character")
    private String password;
    private String repeatPassword;
    @Size(min = 10, max = 15, message = "Phone number contains 10-15 characters")
    private String phoneNumber;

    private String address;
    private City city;
    private String image;
    private String country;
}
