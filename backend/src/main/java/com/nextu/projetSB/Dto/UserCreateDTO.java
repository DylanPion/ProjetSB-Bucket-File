package com.nextu.projetSB.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDTO extends UserDTO {
    private String password;
}

