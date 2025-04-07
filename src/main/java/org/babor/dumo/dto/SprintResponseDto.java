package org.babor.dumo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintResponseDto {
    private int id;
    private String name;
    private Boolean isActive;
}
