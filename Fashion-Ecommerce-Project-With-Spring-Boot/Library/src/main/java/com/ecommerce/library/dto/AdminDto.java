package com.ecommerce.library.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Size(min = 1,max = 15, message = "Invalid first name! (1-15 characters)")
    @Column(name = "first_name", columnDefinition = "nvarchar")
    private String firstName;

    @Size(min = 2,max = 50, message = "Invalid last name! (1-50 characters)")
    @Column(name = "last_name", columnDefinition = "nvarchar")
    private String lastName;

    private String username;
    @Size(min = 5,max = 15, message = "Invalid password! (5-15 characters)")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
            message = "The password must contain at least one digit, one uppercase letter, and one special character")
    private String password;
    private String repeatPassword;

}
