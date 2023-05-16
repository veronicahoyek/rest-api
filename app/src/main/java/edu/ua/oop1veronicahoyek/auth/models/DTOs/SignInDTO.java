package edu.ua.oop1veronicahoyek.auth.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {
    private String username;
    private String password;
}
