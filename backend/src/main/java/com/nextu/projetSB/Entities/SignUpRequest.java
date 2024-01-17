package com.nextu.projetSB.Entities;

import lombok.Data;

@Data
public class SignUpRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}

