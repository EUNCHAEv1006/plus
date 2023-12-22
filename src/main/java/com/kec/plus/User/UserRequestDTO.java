package com.kec.plus.User;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$")
    private String username;

    @Pattern(regexp = "^(?!.*\\b(\\w+)\\b).{4,}$\n")
    private String password;
}