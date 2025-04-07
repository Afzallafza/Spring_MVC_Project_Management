package org.babor.dumo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestDto {
    @NotBlank(message = "Name can not be empty")
    @NotNull(message = "Name can not be null")
    private String name;
    @NotBlank(message = "Description can not be empty")
    @NotNull(message = "Description can not be null")
    private String description;
}
