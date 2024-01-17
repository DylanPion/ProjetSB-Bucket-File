package com.nextu.projetSB.Entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class JwtResponse {
    private final String token;
    private final String id;
    private final String email;
    private final String firstName;
    private final List<String> roles;
    private final Date expirationDate; // Ajout de la Date dans le constructeur
    private static final String TYPE = "Bearer";
}
