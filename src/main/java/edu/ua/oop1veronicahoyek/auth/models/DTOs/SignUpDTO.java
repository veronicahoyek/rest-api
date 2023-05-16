package edu.ua.oop1veronicahoyek.auth.models.DTOs;

import edu.ua.oop1veronicahoyek.auth.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String username;
    private String password;
    private Role role;
}
