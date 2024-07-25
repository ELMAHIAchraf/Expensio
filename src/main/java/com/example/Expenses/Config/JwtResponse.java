package com.example.Expenses.Config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String email;
    private final String jwtToken;

}