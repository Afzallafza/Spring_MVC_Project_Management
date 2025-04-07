package org.babor.dumo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotBlank(message = "Username can not be empty")
    @NotNull(message = "Username can not be null")
    private String name;
    @NotBlank(message = "Username can not be empty")
    @NotNull(message = "Username can not be null")
    private String username;
    @NotBlank(message = "Username can not be empty")
    @NotNull(message = "Username can not be null")
    private String email;
    @NotBlank(message = "Username can not be empty")
    @NotNull(message = "Username can not be null")
    private String password;
}
