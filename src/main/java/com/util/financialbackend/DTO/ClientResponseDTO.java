package com.util.financialbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientResponseDTO {
    private String name;
    private Double salary;
    private String phoneNumber;
    private String email;
}
