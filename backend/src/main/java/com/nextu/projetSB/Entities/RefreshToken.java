package com.nextu.projetSB.Entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
public class RefreshToken {
    private String id;
    private User user;
    private String token;
    private Instant expiryDate;
}
