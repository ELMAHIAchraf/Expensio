package com.example.Expenses.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;
    @NotBlank(message = "First name is required")
    private String Fname;

    @NotBlank(message = "Last name is required")
    private String Lname;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;


}
