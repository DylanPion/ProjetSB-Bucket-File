package com.nextu.projetSB.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String login;
    private String firstName;
    private String lastName;
}
