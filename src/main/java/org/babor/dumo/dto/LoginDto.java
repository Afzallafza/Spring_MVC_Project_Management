package org.babor.dumo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    @NotBlank(message = "Username can not be empty")
    @NotNull(message = "Username can not be null")
    private String username;
    @NotBlank(message = "Password can not be empty")
    @NotNull(message = "Password can not be null")
    private String password;
}
