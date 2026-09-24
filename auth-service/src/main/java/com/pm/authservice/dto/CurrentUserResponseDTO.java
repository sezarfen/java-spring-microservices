package com.pm.authservice.dto;

import java.util.UUID;

public class CurrentUserResponseDTO {
    private UUID id;
    private String email;
    private String role; // TODO: will be updated to enum based Role

    public CurrentUserResponseDTO(UUID id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
